(ns tracker.test.output
  (:use [tracker.output])
  (:use [clojure.test]))


(deftest to-hrs-and-mins-test

  (is (= "" (to-hrs-and-mins 0)))
  (is (= "45m" (to-hrs-and-mins 45)))
  (is (= "1h" (to-hrs-and-mins 60)))
  (is (= "1h 1m" (to-hrs-and-mins 61)))
  (is (= "2h" (to-hrs-and-mins 120)))
  (is (= "2h 1m" (to-hrs-and-mins 121)))


  )
