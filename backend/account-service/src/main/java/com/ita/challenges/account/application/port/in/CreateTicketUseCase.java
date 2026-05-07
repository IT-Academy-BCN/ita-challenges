package com.ita.challenges.account.application.port.in;

import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketResponse;

public interface CreateTicketUseCase {

    TicketResponse execute(TicketRequest request);

}