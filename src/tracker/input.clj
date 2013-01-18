(ns tracker.input)

(defn read-time
  "Parses input string representing time into a number representing the given time in minutes. Currently only supports strings in the form '#m' and '#' where # is an integer number"
  [input]
  (Integer/valueOf (re-find #"\d+" input)))
