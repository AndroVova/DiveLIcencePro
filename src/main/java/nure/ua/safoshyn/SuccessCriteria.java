package nure.ua.safoshyn;

public class SuccessCriteria {


    public static boolean isASuccessfulLesson(double heartRate, Long time, double maxHeartRate, Long maxTime) {
        return isTimeCriteriaIsValid(maxTime, time) && isHeartRateCriteriaIsValid(maxHeartRate, heartRate);
    }
    public static boolean isHeartRateCriteriaIsValid(double maxHeartRate, double heartRate){
        return checkRange(maxHeartRate, heartRate);
    }
    public static boolean isTimeCriteriaIsValid(Long maxTime, Long time){
        return checkRange(maxTime, time);
    }

    public static boolean checkRange(double max, double average) {
        double threshold = 0.2; // Пороговое значение для сравнения (20% от максимального числа)
        double upperLimit =  (max + max * threshold);
        double lowerLimit =  (max - 2 * max * threshold);

        return average <= upperLimit && average >= lowerLimit;
    }
    public static boolean checkRange(Long max, Long average) {
        double threshold = 0.15; // Пороговое значение для сравнения (20% от максимального числа)
        Long upperLimit = (long)(max + max * threshold);
        Long lowerLimit = (long)(max - max * threshold);

        return average <= upperLimit && average >= lowerLimit;
    }
/*
    public static void main(String[] args) {
        System.out.println(isTimeCriteriaIsValid(1800000L, 2000000L));
        System.out.println(isTimeCriteriaIsValid(1800000L, 1500000L));
        System.out.println(isTimeCriteriaIsValid(1800000L, 1700000L));
        System.out.println(isTimeCriteriaIsValid(1800000L, 100000L));


        System.out.println((double)2000000 / 60000);
        System.out.println((double)1500000 / 60000);
        System.out.println((double)1700000 / 60000);
        System.out.println(isHeartRateCriteriaIsValid(120, 140));
        System.out.println(isHeartRateCriteriaIsValid(120, 80));
        System.out.println(isHeartRateCriteriaIsValid(120, 100));
        System.out.println(isHeartRateCriteriaIsValid(120, 60));
    }*/
}
