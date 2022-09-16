package com.overloaded.overloaded;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @PostMapping("/tickerStreamPart1")
    public List<String> tickerStreamPart1(@RequestBody TickerStream.PartOneInput input) {
        return TickerStream.toCumulative(input.stream);
    }

    @PostMapping("/tickerStreamPart2")
    public List<String> tickerStreamPart2(@RequestBody TickerStream.PartTwoInput input) {
        return TickerStream.toCumulativeDelayed(input.stream, input.quantityBlock);
    }
}
