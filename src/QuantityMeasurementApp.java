public class QuantityMeasurementApp {
    static class Length {
        private double value;
        private LengthUnit unit;
        public enum LengthUnit {
            FEET(12.0),
            INCHES(1.0),
            YARDS(36.0),
            CENTIMETERS(0.393701);
            private final double factor;
            LengthUnit(double factor) {
                this.factor = factor;
            }
            public double getFactor() {
                return factor;
            }
        }
        public Length(double value, LengthUnit unit) {
            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");
            this.value = value;
            this.unit = unit;
        }
        private double toInches() {
            return value * unit.getFactor();
        }
        private double fromInches(double inches, LengthUnit target) {
            return inches / target.getFactor();
        }
        public Length convertTo(LengthUnit targetUnit) {
            double inches = toInches();
            double result = fromInches(inches, targetUnit);
            return new Length(round(result), targetUnit);
        }
        public Length add(Length other) {
            if (other == null)
                throw new IllegalArgumentException("Length cannot be null");
            double sumInches = this.toInches() + other.toInches();
            double result = fromInches(sumInches, this.unit);
            return new Length(round(result), this.unit);
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Length other = (Length) obj;
            return Double.compare(this.toInches(), other.toInches()) == 0;
        }
        private double round(double val) {
            return Math.round(val * 100.0) / 100.0;
        }
        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }
    public static Length add(Length l1, Length l2) {
        return l1.add(l2);
    }
    public static void main(String[] args) {
        System.out.println("Input: add(Quantity(1.0, FEET), Quantity(2.0, FEET))");
        System.out.println("Output: " + add(new Length(1.0, Length.LengthUnit.FEET),
                new Length(2.0, Length.LengthUnit.FEET)));
        System.out.println("\nInput: add(Quantity(1.0, FEET), Quantity(12.0, INCHES))");
        System.out.println("Output: " + add(new Length(1.0, Length.LengthUnit.FEET),
                new Length(12.0, Length.LengthUnit.INCHES)));
        System.out.println("\nInput: add(Quantity(12.0, INCHES), Quantity(1.0, FEET))");
        System.out.println("Output: " + add(new Length(12.0, Length.LengthUnit.INCHES),
                new Length(1.0, Length.LengthUnit.FEET)));
        System.out.println("\nInput: add(Quantity(1.0, YARDS), Quantity(3.0, FEET))");
        System.out.println("Output: " + add(new Length(1.0, Length.LengthUnit.YARDS),
                new Length(3.0, Length.LengthUnit.FEET)));
        System.out.println("\nInput: add(Quantity(36.0, INCHES), Quantity(1.0, YARDS))");
        System.out.println("Output: " + add(new Length(36.0, Length.LengthUnit.INCHES),
                new Length(1.0, Length.LengthUnit.YARDS)));
        System.out.println("\nInput: add(Quantity(2.54, CENTIMETERS), Quantity(1.0, INCHES))");
        System.out.println("Output: " + add(new Length(2.54, Length.LengthUnit.CENTIMETERS),
                new Length(1.0, Length.LengthUnit.INCHES)));
        System.out.println("\nInput: add(Quantity(5.0, FEET), Quantity(0.0, INCHES))");
        System.out.println("Output: " + add(new Length(5.0, Length.LengthUnit.FEET),
                new Length(0.0, Length.LengthUnit.INCHES)));
        System.out.println("\nInput: add(Quantity(5.0, FEET), Quantity(-2.0, FEET))");
        System.out.println("Output: " + add(new Length(5.0, Length.LengthUnit.FEET),
                new Length(-2.0, Length.LengthUnit.FEET)));
    }
}