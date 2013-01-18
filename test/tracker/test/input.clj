(ns tracker.test.input
  (:use [tracker.input])
  (:use [clojure.test]))

(deftest read-time-test

  (is (= 45 (read-time "45m")))
  (is (= 40 (read-time "40")))
  (is (= 0 (read-time "0")))

  )
