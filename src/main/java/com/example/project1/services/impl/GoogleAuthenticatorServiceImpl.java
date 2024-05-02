package com.example.project1.services.impl;

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
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class GoogleAuthenticatorServiceImpl {
    private  final GoogleAuthenticator googleAuthenticator;

    public String generateQR(String userName, HttpServletResponse response) throws WriterException, IOException {
        final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(userName);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("my-demo", userName, key);
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(pngData);
        return "data:image/png;base64," + base64Image;
    }

    public boolean isInvalidCode(GoogleValidateCodeRequest googleValidateCodeRequest){
        return googleAuthenticator.authorizeUser(googleValidateCodeRequest.getUserName(), googleValidateCodeRequest.getCode());
    }
}
