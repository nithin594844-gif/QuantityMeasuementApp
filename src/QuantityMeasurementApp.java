public class QuantityMeasurementApp {    static class Length {
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
            if (Double.isNaN(value) || Double.isInfinite(value))
                throw new IllegalArgumentException("Invalid value");
            this.value = value;
            this.unit = unit;
        }
        private double toInches() {
            return value * unit.getFactor();
        }
        public Length convertTo(LengthUnit targetUnit) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");
            double inches = toInches();
            double converted = inches / targetUnit.getFactor();
            return new Length(round(converted), targetUnit);
        }
        private boolean compare(Length other) {
            return Double.compare(this.toInches(), other.toInches()) == 0;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Length other = (Length) obj;
            return compare(other);
        }
        private double round(double val) {
            return Math.round(val * 100.0) / 100.0;
        }
        @Override
        public String toString() {
            return value + " " + unit;
        }
    }
    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }
    public static Length convert(double value, Length.LengthUnit from, Length.LengthUnit to) {
        return new Length(value, from).convertTo(to);
    }
    public static void demonstrateEquality() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
        System.out.println("Input: Quantity(1.0, \"feet\") and Quantity(12.0, \"inches\")");
        System.out.println("Output: Equal (" + demonstrateLengthEquality(l1, l2) + ")");
        Length i1 = new Length(1.0, Length.LengthUnit.INCHES);
        Length i2 = new Length(1.0, Length.LengthUnit.INCHES);
        System.out.println("Input: Quantity(1.0, \"inch\") and Quantity(1.0, \"inch\")");
        System.out.println("Output: Equal (" + demonstrateLengthEquality(i1, i2) + ")");
    }
    public static void demonstrateConversions() {
        System.out.println("Input: convert(1.0, FEET, INCHES) → Output: " + convert(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
        System.out.println("Input: convert(3.0, YARDS, FEET) → Output: " + convert(3.0, Length.LengthUnit.YARDS, Length.LengthUnit.FEET));
        System.out.println("Input: convert(36.0, INCHES, YARDS) → Output: " + convert(36.0, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS));
        System.out.println("Input: convert(1.0, CENTIMETERS, INCHES) → Output: " + convert(1.0, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES));
        System.out.println("Input: convert(0.0, FEET, INCHES) → Output: " + convert(0.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
    }
    public static void main(String[] args) {
        demonstrateEquality();
        demonstrateConversions();
    }
}