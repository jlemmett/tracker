(ns tracker.time
  (:use [clj-time.core :exclude (extend)])
  (:use [clj-time.format]))

;; for streaming dates
(defn inc-date [date] (plus date (days 1)))
(defn dec-date [date] (minus date (days 1)))
(defn inc-date-stream [start-date] (iterate inc-date start-date))
(defn dec-date-stream [start-date] (iterate dec-date start-date))

(defn first-day-of-week
  "Given a date, returns the first date (Monday) of the week that the given date belongs to"
  [date]
  (minus date (days (- (day-of-week date) 1))))

(defn gen-dates
  "Given a date, returns a sequence containing the given date and the num-of-dates dates following that date"
  [start-date num-of-dates]
  (take num-of-dates (inc-date-stream start-date)))

(defn gen-week
  "Given a date, generates a 7 element sequence of the dates of the week that the given date belongs to. Starting from monday, ending in sunday"
  [date]
  (gen-dates (first-day-of-week date) 7))

(defn current-weeks-dates []
  (let [current-date-time (now)
        current-date (date-midnight (year current-date-time) (month current-date-time) (day current-date-time))]
    (gen-week current-date)))






(def basic-formatter (formatter "yyyy-MM-dd"))

;; TODO-RENAME
(defn format-date [date]
  (unparse basic-formatter date))

(defn parse-date [date]
  "Converts a date string in the form 'yyyy-MM-dd' into a datetime instance"
  (parse basic-formatter date))

(defn format-dates [dates]
  (map format-date dates))

(defn format-date-with [formatter date]
  (unparse formatter date))

(def display-formatter (formatter "E d (MMM)"))
