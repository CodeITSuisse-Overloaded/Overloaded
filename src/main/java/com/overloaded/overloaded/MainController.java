package com.overloaded.overloaded;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
//        System.out.println(jsonArrayInput);
        ObjectMapper mapper = new ObjectMapper();
        List<MagicCauldrons.Input> inputList = mapper.readValue(
                jsonArrayInput, new TypeReference<List<MagicCauldrons.Input>>() { }
        );


        List<MagicCauldrons.Output> res = new ArrayList<>();
        for (MagicCauldrons.Input input : inputList) {
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

    @PostMapping(value="/travelling-suisse-robot", consumes = "text/plain", produces = "text/plain")
    public String travellingSuisseRobot(@RequestBody String input) {
        System.out.println(input);
        return TravellingSuisseRobot.solve(input);
    }

    @PostMapping(value="/calendarDays", consumes = "application/json", produces = "application/json")
    public CalendarDays.Output calendarDays(@RequestBody CalendarDays.Input input) {
        System.out.println(input);
        String part1 = CalendarDays.partOne(input);
        List<Integer> part2 = CalendarDays.partTwo(part1);
        System.out.println(part1);
        System.out.println(part2);
        return new CalendarDays.Output(part1, part2);
    }

    @PostMapping(value="/cryptoCollapz", consumes = "application/json", produces = "application/json")
    public List<List<Integer>> cryptoCollapz(@RequestBody List<List<Integer>> input) {
        System.out.println(input);
        List<List<Integer>> res = CryptoCollapz.cryptoCollapz(input);
        System.out.println(res);
        return res;
    }
}
