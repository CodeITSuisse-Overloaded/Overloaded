package com.overloaded.overloaded;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/magiccauldrons")
    public List<MagicCauldrons.Output> magicCauldrons(@RequestBody String jsonArrayInput)
            throws JsonProcessingException {
        System.out.println(jsonArrayInput);
        ObjectMapper mapper = new ObjectMapper();
        List<MagicCauldrons.Input> inputList = mapper.readValue(
                jsonArrayInput, new TypeReference<List<MagicCauldrons.Input>>() { });

        System.out.println(inputList);

        List<MagicCauldrons.Output> res = new ArrayList<>();
        System.out.println(inputList);
        for (MagicCauldrons.Input input : inputList) {
            System.out.println(input);
            res.add(
                    new MagicCauldrons.Output(
                            MagicCauldrons.partOne(input.part1),
                            MagicCauldrons.partTwo(input.part2),
                            MagicCauldrons.partThree(input.part3),
                            MagicCauldrons.partFour(input.part4)
                    )
            );
        }

        return res;
    }
}
