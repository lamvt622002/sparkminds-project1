package com.example.project1.controllers;

import com.example.project1.payload.request.GoogleValidateCodeRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class TestController {
   private final GoogleAuthenticator googleAuthenticator;
    @GetMapping("/generate/{username}")
    public void generate(@PathVariable String username, HttpServletResponse response) throws WriterException, IOException {
        final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(username);

        //I've decided to generate QRCode on backend site
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("my-demo", username, key);

        System.out.println(otpAuthURL);

        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

        //Simple writing to outputstream
        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();
    }
    @PostMapping("/validate/key")

    public boolean validateKey(@RequestBody GoogleValidateCodeRequest body) {
        System.out.println(  googleAuthenticator.authorizeUser(body.getUserName(), body.getCode()));
        return googleAuthenticator.authorizeUser(body.getUserName(), body.getCode());
    }

}
