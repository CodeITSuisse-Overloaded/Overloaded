package com.overloaded.overloaded;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @PostMapping("/tickerStreamPart1")
    public TickerStream.PartOneOutput tickerStreamPart1(@RequestBody TickerStream.PartOneInput input) {
        System.out.println(input.stream);
        return new TickerStream.PartOneOutput(TickerStream.toCumulative(input.stream));
    }

    @PostMapping("/tickerStreamPart2")
    public TickerStream.PartTwoOutput tickerStreamPart2(@RequestBody TickerStream.PartTwoInput input) {
        System.out.println(input.stream);
        return new TickerStream.PartTwoOutput(TickerStream.toCumulativeDelayed(input.stream, input.quantityBlock));
    }
}
