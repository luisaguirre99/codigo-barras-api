package com.example.codigobarras;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

@RestController
public class CodigoBarrasController {

    @GetMapping("/codigo")
    public void generarCodigo(
            @RequestParam String gtin,
            @RequestParam String tematica,
            @RequestParam String recibo,
            @RequestParam int valor,
            @RequestParam String fecha,
            HttpServletResponse response) throws Exception {

        char fnc1 = 29;
        String contenido = "" +
                fnc1 + "415" + gtin +
                fnc1 + "8020" + tematica + recibo +
                fnc1 + "3900" + String.format("%015d", valor) +
                fnc1 + "96" + fecha.replace("-", "");

        Code128Bean bean = new Code128Bean();
        bean.setHeight(15.0);
        bean.setModuleWidth(0.18);
        bean.setQuietZone(2);
        bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        bean.doQuietZone(true);

        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", 300,
                BufferedImage.TYPE_BYTE_BINARY, false, 0);
        bean.generateBarcode(canvas, contenido);
        canvas.finish();
        out.close();
    }
}
