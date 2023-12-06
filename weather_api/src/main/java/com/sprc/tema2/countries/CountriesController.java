package main.java.com.sprc.tema2.countries;

@RestController
@RequestMapping("/countries")
@CrossOrigin
public class CountriesController {

    @Autowired
    private CountriesService countriesService;

    @Autowired
    private CountriesRepository countriesRepository;

    @PostMapping("")
    public ResponseEntity<String> addCountry(@RequestBody Map<String, String> mapCountry) {

        Country country = new Country(map.get("nume"), map.get("lat"), map.get("lon"));

        if (countriesService.saveCountry(country))
            return new ResponseEntity<>("Country added successfully.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Country already exists in the database.", HttpStatus.CONFLICT);
    }
}
