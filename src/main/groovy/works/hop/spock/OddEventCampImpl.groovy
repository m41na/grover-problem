package works.hop.spock

class OddEventCampImpl implements OddEvenCamp{

    Validator<Integer> validator;

    @Override
    int check(int number) {
        this.validator.validate(number)
        return number % 2 == 0 ? 1 : 0
    }
}
