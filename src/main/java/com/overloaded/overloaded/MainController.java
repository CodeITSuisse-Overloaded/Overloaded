package com.overloaded.overloaded;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    @PostMapping("/tickerStreamPart1")
    public List<String> tickerStreamPart1(@RequestBody Map<String, Object> payload) throws ClassCastException{
        if (payload.get("stream") instanceof List
                && ((List<?>) payload.get("stream")).get(0) instanceof String) {
            List<String> ticks = new ArrayList<>();
            for (Object tick : (List<?>) payload.get("stream")) {
                ticks.add((String) tick);
            }

            return TickerStream.toCumulative(ticks);

        } else {
            return null;
        }
    }

    @PostMapping("/tickerStreamPart2")
    public List<String> tickerStreamPart2(@RequestBody Map<String, Object> payload) throws ClassCastException{
        List<String> ticks = new ArrayList<>();
        int quantityBlock = 0;
        if (payload.get("stream") instanceof List
                && ((List<?>) payload.get("stream")).get(0) instanceof String) {
            for (Object tick : (List<?>) payload.get("stream")) {
                ticks.add((String) tick);
            }
        }
        if (payload.get("quantityBlock") instanceof Integer) {
            quantityBlock = (Integer) payload.get("quantityBlock");
        }

        return TickerStream.toCumulativeDelayed(ticks, quantityBlock);
    }
}
