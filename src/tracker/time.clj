(ns tracker.time
  (:use [clj-time.core :exclude (extend)])
  (:use [clj-time.format]))

(defn first-day-of-week
  "Given a date, returns the first date (Monday) of the week that the given date belongs to"
  [date]
  (minus date (days (- (day-of-week date) 1))))

(defn gen-dates
  "Given a date, returns a sequence containing the given date and the num-of-dates dates following that date"
  [start-date num-of-dates]
  (for [x (range 0 num-of-dates)
        :let [date (plus start-date (days x))]]
    date))

(defn gen-week
  "Given a date, generates a 7 element sequence of the dates of the week that the given date belongs to. Starting from monday, ending in sunday"
  [date]
  (gen-dates (first-day-of-week date) 7))

(defn current-weeks-dates []
  (gen-week (now)))

(def basic-formatter (formatter "yyyy-MM-dd"))

;; TODO-RENAME
(defn format-date [date]
  (unparse basic-formatter date))

(defn parse-date [date]
  (parse basic-formatter date))

(defn format-dates [dates]
  (map format-date dates))

(defn format-date-with [formatter date]
  (unparse formatter date))

(def display-formatter (formatter "E d (MMM)"))
