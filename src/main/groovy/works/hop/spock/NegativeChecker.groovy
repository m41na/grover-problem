package works.hop.spock

class NegativeChecker implements Validator<Integer>{

    @Override
    void validate(Integer value) {
        if(value == null || value <= 0){
            throw new NegativeNumberException("Value is a 0 or is negative")
        }
    }
}
