package works.hop.spock

interface Validator<T> {

    void validate(T value)
}