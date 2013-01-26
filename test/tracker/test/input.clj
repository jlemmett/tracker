(ns tracker.test.input
  (:use [tracker.input])
  (:use [clojure.test]))

(deftest read-time-test

  (is (= 45 (read-time "45m")))
  (is (= 40 (read-time "40")))
  (is (= 0 (read-time "0")))

  )


(deftest test-add-task-entry

  (let [tasks (ref {})]


    (add-task-entry :user1 :21-06-1984 {:time 45 :work-category :category :tags "tags"} tasks)
    (is (= {:user1 {:21-06-1984 [{:time 45 :work-category :category :tags "tags"}]}} @tasks))



    (add-task-entry :user2 :22-06-1984 {:time 50 :work-category :category2 :tags "new-tags"} tasks)
    (is (=

         {
          :user1 {:21-06-1984 [{:time 45 :work-category :category :tags "tags"}]}
          :user2 {:22-06-1984 [{:time 50 :work-category :category2 :tags "new-tags"}]}
          }

         @tasks))

    )


    )
