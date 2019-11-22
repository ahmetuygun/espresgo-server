package com.example.polls.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.polls.model.*;
import com.example.polls.model.rto.*;
import com.example.polls.repository.CoffeeDetailRepository;
import com.example.polls.util.AppConstants;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.polls.repository.AddressRepository;
import com.example.polls.repository.CoffeeOrderRepository;
import com.example.polls.repository.OrderSelectionRepository;
import com.example.polls.security.UserPrincipal;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class CoffeeService {

    @Autowired
    CoffeeOrderRepository orderRepository;

    @Autowired
    OrderSelectionRepository selectionRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UtilService utilService;

    FirebaseOptions options;
    FileInputStream serviceAccount;


    @Autowired
    OrderSelectionRepository orderSelectionRepository;
    @Autowired
    CoffeeDetailRepository coffeeDetailRepository;

    public CoffeeService() throws FileNotFoundException {
    }

    public GenericResponse insertOrder(OrderRequest request, UserPrincipal currentUser) {
        String uui = "" + ((int) (Math.random() * 900000) + 100000);
        String orderDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());


        if (utilService.get(AppConstants.ORDER_STATUS, AppConstants.CLOSE)) {

            return GenericResponse.builder().
                    message(AppConstants.ResponseCodeMessage.ORDER_CLOSED).
                    status(0).
                    responseCode(AppConstants.ResponseCode.ORDER_CLOSED).build();

        }
        ;
        Integer activeOrderCount = orderRepository.countActiveOrders();

        if (activeOrderCount > 40) {

            return GenericResponse.builder().
                    message(AppConstants.ResponseCodeMessage.ORDER_CLOSED).
                    status(0).
                    responseCode(AppConstants.ResponseCode.ORDER_CLOSED).build();
        }


        if ((activeOrderCount > 20 && !request.isSendLaterOption() && !request.isSendLaterOk())) {

            return GenericResponse.builder().
                    message(AppConstants.ResponseCodeMessage.BUSY + "Dilerseniz 25 dakika sonra göndermek üzere sıraya alalım. ").
                    status(0).
                    responseCode(AppConstants.ResponseCode.BUSY).build();
        }


        try {
            Optional<Adress> adress = addressRepository.findByUser(currentUser.getId());
            StringBuilder itemNames = new StringBuilder();

            request.getOrders().forEach(orderRequest -> {

                CoffeeDetail coffeeDetail = coffeeDetailRepository.findById(orderRequest.getId()).get();
                CoffeeOrder coffeeOrder = orderRepository.save(CoffeeOrder.builder()
                        .adress(adress.get())
                        .coffee(coffeeDetail)
                        .orderUID(uui)
                        .status(AppConstants.NEW_ORDER)
                        .totalPrice(orderRequest.getTotalPrice())
                        .productAmount(orderRequest.getProductAmount())
                        .user(new User(currentUser.getId())).build());

                itemNames.append(coffeeDetail.getCoffee().getTitle() +" x" +orderRequest.getProductAmount()+ " ") ;

                if (coffeeOrder != null && coffeeOrder.getId() != null) {

                    orderRequest.getSelection().forEach(selection -> {

                        selectionRepository.save(OrderSelection.builder()
                                .coffeeOrder(coffeeOrder)
                                .selection(new Selection(selection.getId()))
                                .selectionItem(new SelectionItem(selection.getItems().get(0).getId())).build());

                    });
                }
            });

            RestTemplate restTemplate = new RestTemplate();

            StringBuilder addressText = new StringBuilder();
            addressText.append(adress.get()!=null && adress.get().getCity()!=null ? adress.get().getCity().getName() + "-": "Sehir:? -");
            addressText.append(adress.get()!=null && adress.get().getDistrict()!=null ? adress.get().getDistrict().getName()  + "-": "İlçe:? -");
            addressText.append(adress.get()!=null && adress.get().getBuilding()!=null ? adress.get().getBuilding().getName()+ "-": "Bina:? -");
            addressText.append(adress.get()!=null && adress.get().getCompany()!=null ? adress.get().getCompany().getName(): "Şirket:? -");

            PushOrderRequest pushOrderRequest = PushOrderRequest.builder()
                    .orderUid(uui)
                    .orderDate(orderDate)
                    .addessText(addressText.toString())
                    .orderItemNames(itemNames.toString())
                    .status(String.valueOf(AppConstants.NEW_ORDER))
                    .build();
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/pushOrder",
                    pushOrderRequest,
                    String.class);

        } catch (Exception e) {

            return GenericResponse.builder().
                    message("Sipariş alınamadı. Whatsapp dan göndermeyi deneyin").
                    status(0).
                    responseCode(101).build();
        }




        return GenericResponse.builder()
                .message("Siparişiniz Alındı!")
                .status(1)
                .responseCode(100)
                .orderId(uui)
                .orderTime(orderDate)
                .contactNumber(currentUser.getUsername())
                .build();

    }

    public OrderHistoryRto getOrders(UserPrincipal currentUser, String method) {

        List<CoffeeOrder> orderList = new ArrayList<CoffeeOrder>();
        if (AppConstants.NEWORDERS_METHOD.equals(method)) {
            orderList = orderRepository.findByStatusOrderByUpdatedAtDesc(AppConstants.NEW_ORDER);
        } else if (AppConstants.HISTORY_METHOD.equals(method)) {
            List<String> uidList = orderRepository.findLast5UidByUser(currentUser.getId());
            orderList = orderRepository.findByOrderUIDInOrderByCreatedAtDesc(uidList);
        }

        Map<String, List<CoffeeOrderRto>> last5Orders = new HashMap<String, List<CoffeeOrderRto>>();

        orderList.forEach(orderDb -> {

            if (last5Orders.get(orderDb.getOrderUID()) != null) {
                last5Orders.get(orderDb.getOrderUID()).add(getOrderRequest(orderDb));
            } else {
                List<CoffeeOrderRto> list = new ArrayList<CoffeeOrderRto>();
                list.add(getOrderRequest(orderDb));
                last5Orders.put(orderDb.getOrderUID(), list);
            }
        });


        List<OrderCheckOutRto> listForResponse = new ArrayList<OrderCheckOutRto>();

        for (Map.Entry<String, List<CoffeeOrderRto>> entry : last5Orders.entrySet()) {

            String orderDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(entry.getValue().get(0).getOrderDate());

            List<CoffeeOrderRto> list = entry.getValue();
            BigDecimal checkOutPrice = BigDecimal.ZERO;

            for (CoffeeOrderRto item : list) {
                if (item.getTotalPrice() != null)
                    checkOutPrice = checkOutPrice.add(item.getTotalPrice());
            }

            String statusAsString = getStatus(entry.getValue().get(0));

            listForResponse.add(OrderCheckOutRto.builder().
                    orders(entry.getValue()).
                    orderDate(orderDate).
                    checkOutPrice(checkOutPrice).
                    orderUid(entry.getKey()).
                    status(statusAsString).
                    address(entry.getValue().get(0).getAddress()).
                    user(entry.getValue().get(0).getUser()).
                    orderAsDate(entry.getValue().get(0).getOrderDate()).
                    statusId(entry.getValue().get(0).getStatus()).
                    build());
        }

        OrderHistoryRto orderHistoryRto = new OrderHistoryRto();

        Collections.sort(listForResponse);
        orderHistoryRto.setHistory(listForResponse);


        return orderHistoryRto;
    }

    private String getStatus(CoffeeOrderRto entry) {

        switch (entry.getStatus()) {
            case 0:
                return "Sipariş alındı.";
            case 1:
                return "Hazırlanıyor.";
            case 2:
                return "Gönderildi";
            case 3:
                return "Teslim alındı";
            default:
                return "";
        }
    }

    private CoffeeOrderRto getOrderRequest(CoffeeOrder orderDb) {

        List<OrderSelectionRto> selections = new ArrayList<OrderSelectionRto>();

        orderDb.getSelections().forEach(element -> {
            OrderSelectionRto selection = OrderSelectionRto.builder().
                    selection(SelectionRto.builder().label(element.getSelection().getLabel()).build()).
                    selectionItem(SelectionItemRto.builder().itemName(element.getSelectionItem().getItemName()).build()).
                    build();
            selections.add(selection);
        });


        return CoffeeOrderRto.builder()
                .amount(orderDb.getProductAmount())
                .selection(selections)
                .status(orderDb.getStatus())
                .title(orderDb.getCoffee().getCoffee().getTitle())
                .totalPrice(orderDb.getTotalPrice())
                .address(getAddressRto(orderDb.getAdress()))
                .user(UserPrincipal.create(orderDb.getUser()))
                .orderDate(Date.from(orderDb.getCreatedAt()))
                .build();

    }

    private AdressRto getAddressRto(Adress adress) {

        AdressRto adressRto = null;

        try {

            CityRto cityRto = CityRto.builder()
                    .name(adress.getCity().getName())
                    .id(adress.getCity().getId())
                    .build();

            DistrictRto districtRto = DistrictRto.builder()
                    .name(adress.getDistrict().getName())
                    .id(adress.getDistrict().getId())
                    .build();

            BuildingRto buildingRto = BuildingRto.builder()
                    .name(adress.getBuilding().getName())
                    .id(adress.getBuilding().getId())
                    .build();

            CompanyRto companyRto = CompanyRto.builder()
                    .name(adress.getCompany().getName())
                    .id(adress.getCompany().getId())
                    .build();

            adressRto = AdressRto.builder()
                    .city(cityRto)
                    .district(districtRto)
                    .building(buildingRto)
                    .company(companyRto)
                    .build();
        } catch (Exception e) {

            return null;
        }

        return adressRto;

    }

    public boolean updateOrderStatus(UpdateOrderStatusRequest request) {

        try {
            List<CoffeeOrder> orderList = orderRepository.findByOrderUIDInOrderByCreatedAtDesc(Arrays.asList(request.getOrderUid()));
            orderList.forEach(item -> {
                item.setStatus(request.getStatus());
                orderRepository.save(item);
            });

            return true;
        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

    }
}
