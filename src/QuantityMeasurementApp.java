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
        public double fromBase(double baseValue) {
            return baseValue / factor;
        }
    }
    static class Length {
        private double value;
        private LengthUnit unit;
        public Length(double value, LengthUnit unit) {
            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");
            this.value = value;
            this.unit = unit;
        }
        private double toBase() {
            return unit.toBase(value);
        }
        private double fromBase(double base, LengthUnit target) {
            return target.fromBase(base);
        }
        public Length convertTo(LengthUnit targetUnit) {
            double base = toBase();
            double result = fromBase(base, targetUnit);
            return new Length(round(result), targetUnit);
        }
        public Length add(Length other) {
            double sumBase = this.toBase() + other.toBase();
            double result = fromBase(sumBase, this.unit);
            return new Length(round(result), this.unit);
        }
        public Length add(Length other, LengthUnit targetUnit) {
            double sumBase = this.toBase() + other.toBase();
            double result = fromBase(sumBase, targetUnit);
            return new Length(round(result), targetUnit);
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Length other = (Length) obj;
            return Double.compare(this.toBase(), other.toBase()) == 0;
        }
        private double round(double val) {
            return Math.round(val * 1000.0) / 1000.0;
        }
        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }
    public static void main(String[] args) {
        System.out.println("\nInput: Quantity(1.0, FEET).convertTo(INCHES)");
        System.out.println("Output: " + new Length(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES));
        System.out.println("\nInput: add(Quantity(1.0, FEET), Quantity(12.0, INCHES), FEET)");
        System.out.println("Output: " + new Length(1.0, LengthUnit.FEET)
                .add(new Length(12.0, LengthUnit.INCHES), LengthUnit.FEET));
        System.out.println("\nInput: equals(36 INCHES, 1 YARD)");
        System.out.println("Output: " + new Length(36.0, LengthUnit.INCHES)
                .equals(new Length(1.0, LengthUnit.YARDS)));
        System.out.println("\nInput: add(1 YARD, 3 FEET, YARDS)");
        System.out.println("Output: " + new Length(1.0, LengthUnit.YARDS)
                .add(new Length(3.0, LengthUnit.FEET), LengthUnit.YARDS));
        System.out.println("\nInput: convert(2.54 CM → INCHES)");
        System.out.println("Output: " + new Length(2.54, LengthUnit.CENTIMETERS)
                .convertTo(LengthUnit.INCHES));
        System.out.println("\nInput: add(5 FEET, 0 INCHES, FEET)");
        System.out.println("Output: " + new Length(5.0, LengthUnit.FEET)
                .add(new Length(0.0, LengthUnit.INCHES), LengthUnit.FEET));
        System.out.println("\nInput: LengthUnit.FEET.toBase(1.0)");
        System.out.println("Output: " + LengthUnit.FEET.toBase(1.0));
        System.out.println("\nInput: LengthUnit.INCHES.fromBase(12.0)");
        System.out.println("Output: " + LengthUnit.INCHES.fromBase(12.0));
    }
}