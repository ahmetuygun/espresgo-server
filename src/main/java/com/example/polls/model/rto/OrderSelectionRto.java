package com.example.polls.model.rto;

import com.example.polls.model.CoffeeOrder;
import com.example.polls.model.Selection;
import com.example.polls.model.SelectionItem;
import com.example.polls.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSelectionRto {

	private static final long serialVersionUID = 1L;

    private SelectionRto selection;

    private SelectionItemRto selectionItem;
    
    
}
