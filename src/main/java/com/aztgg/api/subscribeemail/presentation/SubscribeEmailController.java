package com.aztgg.api.subscribeemail.presentation;

import com.aztgg.api.subscribeemail.application.SubscribeEmailService;
import com.aztgg.api.subscribeemail.application.dto.CreateSubscribeEmailRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/subscribe-emails")
public class SubscribeEmailController implements SubscribeEmailApi {

    private final SubscribeEmailService subscribeEmailService;

    @Override
    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createSubscribeEmail(@Valid @RequestBody CreateSubscribeEmailRequestDto payload) {
        subscribeEmailService.createSubscribeEmail(payload);
    }

    @Override
    @GetMapping("/{email}/unsubscribe")
    public String unsubscribeEmail(@PathVariable String email) {
        subscribeEmailService.delete(email);
        return email + ", UNSUBSCRIBE SUCCESS";
    }
}
