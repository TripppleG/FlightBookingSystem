package com.flightbookingsystem.services.implementations;

import com.flightbookingsystem.data.entity.Ticket;
import com.flightbookingsystem.data.repository.TicketRepository;
import com.flightbookingsystem.dto.CreateTicketDTO;
import com.flightbookingsystem.dto.TicketDTO;
import com.flightbookingsystem.dto.UpdateTicketDTO;
import com.flightbookingsystem.exceptions.TicketNotFoundException;
import com.flightbookingsystem.services.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Validated
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;
    private ModelMapper modelMapper;

    private TicketDTO convertToTicketDTO(Ticket ticket) {
        return modelMapper.map(ticket, TicketDTO.class);
    }

    @Override
    public List<TicketDTO> getTickets() {
        return ticketRepository.findAll().stream()
                .map(this::convertToTicketDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO getTicket(String bookingReference) {
        return modelMapper.map(ticketRepository.findById(bookingReference)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with bookingReference " + bookingReference + " not found")), TicketDTO.class);
    }

    @Override
    public Ticket create(@Valid CreateTicketDTO createTicketDTO) {
        return ticketRepository.save(modelMapper.map(createTicketDTO, Ticket.class));
    }

    @Override
    public Ticket updateTicket(String bookingReference, @Valid UpdateTicketDTO updateTicketDTO) {
        Ticket ticket = modelMapper.map(updateTicketDTO, Ticket.class);
        ticket.setBookingReference(bookingReference);
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(String bookingReference) {
        ticketRepository.deleteById(bookingReference);
    }
}
