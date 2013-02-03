(ns tracker.test.time
  (:use [tracker.time])
  (:use [clj-time.core :exclude (extend)])
  (:use [clojure.test]))

(deftest test-gen-week


  (is (= [(date-time 2013 1 28)
           (date-time 2013 1 29)
           (date-time 2013 1 30)
           (date-time 2013 1 31)
           (date-time 2013 2 1)
           (date-time 2013 2 2)
           (date-time 2013 2 3)]

         (gen-week (date-time 2013 2 3))))
  )
