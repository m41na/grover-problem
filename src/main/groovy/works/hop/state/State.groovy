package works.hop.state

import works.hop.Dir

abstract class State {

    int x
    int y
    Dir dir

    abstract State left();

    abstract State right();

    abstract State move(int mx, int my);

    String position() {
        return String.format("%d %d %s", this.x, this.y, this.dir)
    }

    static State newState(int x, int y, Dir dir){
        switch (dir){
            case Dir.N:
                return new Northern(x: x, y: y, dir: dir)
            case Dir.E:
                return new Eastern(x: x, y: y, dir: dir)
            case Dir.S:
                return new Southern(x: x, y: y, dir: dir)
            case Dir.W:
                return new Western(x: x, y: y, dir: dir)
            default:
                return null
        }
    }
}

class Northern extends State {

    @Override
    State left() {
        return new Western(x: this.x, y: this.y, dir: Dir.W)
    }

    @Override
    State right() {
        return new Eastern(x: this.x, y: this.y, dir: Dir.E)
    }

    @Override
    State move(int mx, int my) {
        if (this.y + 1 <= my) {
            this.y++
        }
        return this
    }
}

class Eastern extends State {

    @Override
    State left() {
        return new Northern(x: this.x, y: this.y, dir: Dir.N)
    }

    @Override
    State right() {
        return new Southern(x: this.x, y: this.y, dir: Dir.S)
    }

    @Override
    State move(int mx, int my) {
        if (this.x + 1 <= mx) {
            this.x++
        }
        return this
    }
}

class Southern extends State {

    @Override
    State left() {
        return new Eastern(x: this.x, y: this.y, dir: Dir.E)
    }

    @Override
    State right() {
        return new Western(x: this.x, y: this.y, dir: Dir.W)
    }

    @Override
    State move(int mx, int my) {
        if (this.y - 1 >= 0) {
            this.y--
        }
        return this
    }
}

class Western extends State {

    @Override
    State left() {
        return new Southern(x: this.x, y: this.y, dir: Dir.S)
    }

    @Override
    State right() {
        return new Northern(x: this.x, y: this.y, dir: Dir.N)
    }

    @Override
    State move(int mx, int my) {
        if (this.x - 1 >= 0) {
            this.x--
        }
        return this
    }
}
