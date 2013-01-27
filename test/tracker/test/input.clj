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

    ;; adding new user entries
    (add-task-entry :user1 :21-06-1984 {:time 45 :work-category :category1 :tags "tags1"} tasks)
    (is (= {:user1 {:21-06-1984 [{:time 45 :work-category :category1 :tags "tags1"}]}} @tasks))


    (add-task-entry :user2 :22-06-1984 {:time 50 :work-category :category2 :tags "tags2"} tasks)
    (is (=

         {
          :user1 {:21-06-1984 [{:time 45 :work-category :category1 :tags "tags1"}]}
          :user2 {:22-06-1984 [{:time 50 :work-category :category2 :tags "tags2"}]}
          }

         @tasks))
    )


  ;; adding new date entries to existing user
  (let [tasks (ref {})]

    (add-task-entry :user1 :21-06-1984 {:time 45 :work-category :category1 :tags "tags1"} tasks)
    (is (= {:user1 {:21-06-1984 [{:time 45 :work-category :category1 :tags "tags1"}]}} @tasks))

    (add-task-entry :user1 :22-06-1984 {:time 45 :work-category :category1 :tags "tags1"} tasks)
    (is (=

         {
          :user1 {:21-06-1984 [{:time 45 :work-category :category1 :tags "tags1"}]
                  :22-06-1984 [{:time 45 :work-category :category1 :tags "tags1"}]
                  }
          }

         @tasks))
    )

  ;; adding new task entries to existing date entry of existing user
  (let [tasks (ref {})]

    (add-task-entry :user1 :21-06-1984 {:time 45 :work-category :category1 :tags "tags1"} tasks)
    (is (= {:user1 {:21-06-1984 [{:time 45 :work-category :category1 :tags "tags1"}]}} @tasks))

    (add-task-entry :user1 :21-06-1984 {:time 50 :work-category :category2 :tags "tags2"} tasks)
    (is (=

         {
          :user1 {:21-06-1984 [{:time 45 :work-category :category1 :tags "tags1"}
                               {:time 50 :work-category :category2 :tags "tags2"}]
                  }
          }

         @tasks))
    )
  )
