package com.tobeto.hotelReservationSystem.services.concretes;

import com.tobeto.hotelReservationSystem.core.utils.exceptions.types.BusinessException;
import com.tobeto.hotelReservationSystem.entities.Payment;
import com.tobeto.hotelReservationSystem.repositories.PaymentRepository;
import com.tobeto.hotelReservationSystem.services.abstracts.PaymentService;
import com.tobeto.hotelReservationSystem.services.dtos.requests.payment.AddPaymentRequest;
import com.tobeto.hotelReservationSystem.services.dtos.requests.payment.UpdatePaymentRequest;
import com.tobeto.hotelReservationSystem.services.dtos.responses.payment.AddPaymentResponse;
import com.tobeto.hotelReservationSystem.services.dtos.responses.payment.GetByIdPaymentResponse;
import com.tobeto.hotelReservationSystem.services.dtos.responses.payment.ListPaymentResponse;
import com.tobeto.hotelReservationSystem.services.dtos.responses.payment.UpdatePaymentResponse;
import com.tobeto.hotelReservationSystem.services.mappers.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public AddPaymentResponse add(AddPaymentRequest request) {
        if (request == null){
            throw new BusinessException("Payment cannot be null");
        }

        Payment payment = PaymentMapper.INSTANCE.addPaymentRequestToPayment(request);
        Payment saved = paymentRepository.save(payment);

        return PaymentMapper.INSTANCE.paymentToAddPaymentResponse(saved);
    }

    @Override
    public UpdatePaymentResponse update(UpdatePaymentRequest request) {
        if(!paymentRepository.existsById(request.getId())){
            throw  new BusinessException("Payment is not found");
        }
        Payment payment = PaymentMapper.INSTANCE.updatePaymentRequestToPayment(request);
        Payment updated = paymentRepository.save(payment);

        return PaymentMapper.INSTANCE.paymentToUpdatePaymentResponse(updated);
    }

    @Override
    public List<ListPaymentResponse> getAll() {
        List<Payment> payments = paymentRepository.findAll();
        List<ListPaymentResponse> result = new ArrayList<>();

        for (Payment payment:payments){
            ListPaymentResponse dto= PaymentMapper.INSTANCE.paymentsToListPaymentResponses(payment);
            result.add(dto);
        }

        return result;
    }

    @Override
    public GetByIdPaymentResponse getById(int id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment is not found"));
        return PaymentMapper.INSTANCE.paymentToGetByIdPaymentResponse(payment);

    }

    @Override
    public void delete(int id) {
        if(!paymentRepository.existsById(id)){
            throw new BusinessException("Feedback is not found");
        }
        paymentRepository.deleteById(id);
    }
}
