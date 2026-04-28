public class QuantityMeasurementApp {
    enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);
        private final double factor;
        LengthUnit(double factor) {
            this.factor = factor;
        }
        public double toBase(double value) {
            return value * factor;
        }
        public double fromBase(double base) {
            return base / factor;
        }
    }
    enum WeightUnit {
        MILLIGRAM(0.001),
        GRAM(1.0),
        KILOGRAM(1000.0),
        POUND(453.592),
        TONNE(1_000_000.0);
        private final double factor;
        WeightUnit(double factor) {
            this.factor = factor;
        }
        public double toBase(double value) {
            return value * factor;
        }
        public double fromBase(double base) {
            return base / factor;
        }
    }
    static class Length {
        private double value;
        private LengthUnit unit;
        public Length(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }
        private double toBase() {
            return unit.toBase(value);
        }
        public Length convertTo(LengthUnit target) {
            double base = toBase();
            return new Length(round(target.fromBase(base)), target);
        }
        public Length add(Length other) {
            double sum = this.toBase() + other.toBase();
            return new Length(round(unit.fromBase(sum)), unit);
        }
        public Length add(Length other, LengthUnit target) {
            double sum = this.toBase() + other.toBase();
            return new Length(round(target.fromBase(sum)), target);
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Length)) return false;
            Length l = (Length) o;
            return Double.compare(this.toBase(), l.toBase()) == 0;
        }
        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }
    static class Weight {
        private double value;
        private WeightUnit unit;
        public Weight(double value, WeightUnit unit) {
            this.value = value;
            this.unit = unit;
        }
        private double toBase() {
            return unit.toBase(value);
        }
        public Weight convertTo(WeightUnit target) {
            double base = toBase();
            return new Weight(round(target.fromBase(base)), target);
        }
        public Weight add(Weight other) {
            double sum = this.toBase() + other.toBase();
            return new Weight(round(unit.fromBase(sum)), unit);
        }
        public Weight add(Weight other, WeightUnit target) {
            double sum = this.toBase() + other.toBase();
            return new Weight(round(target.fromBase(sum)), target);
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Weight)) return false;
            Weight w = (Weight) o;
            return Double.compare(this.toBase(), w.toBase()) == 0;
        }
        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }
    static double round(double v) {
        return Math.round(v * 1000.0) / 1000.0;
    }
    public static void main(String[] args) {
        System.out.println(new Length(1, LengthUnit.FEET).convertTo(LengthUnit.INCHES));
        System.out.println(new Length(1, LengthUnit.FEET).add(new Length(12, LengthUnit.INCHES), LengthUnit.FEET));
        System.out.println(new Length(36, LengthUnit.INCHES).equals(new Length(1, LengthUnit.YARDS)));
        System.out.println(new Weight(1, WeightUnit.KILOGRAM).equals(new Weight(1000, WeightUnit.GRAM)));
        System.out.println(new Weight(2, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM));
        System.out.println(new Weight(500, WeightUnit.GRAM).add(new Weight(0.5, WeightUnit.KILOGRAM)));
        System.out.println(new Weight(1, WeightUnit.KILOGRAM).add(new Weight(1000, WeightUnit.GRAM), WeightUnit.GRAM));
        System.out.println("Weight vs Length comparison not allowed");
    }
}