(ns tracker.test.input
  (:use [tracker.input])
  (:use [clojure.test])
  (:use [clj-time.core :exclude (extend)]))

(deftest read-time-test

  (is (= 45 (read-time "45m")))
  (is (= 40 (read-time "40")))
  (is (= 0 (read-time "0")))

  )

(deftest add-task-entry-test

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


(deftest dates-and-worked-hours-test

  (let [dates []
        dates-and-tasks {}]

    (is (= {} (dates-and-worked-hours dates dates-and-tasks))))

  (let [dates [(date-time 2013 2 16)]
        dates-and-tasks {(date-time 2013 2 16) [{:time 15} {:time 30}]}]

    (is (= {(date-time 2013 2 16) 45} (dates-and-worked-hours dates dates-and-tasks))))

  (let [dates [(date-time 2013 2 16)]
        dates-and-tasks {(date-time 2013 2 16) [{:time 15} {:time 30}]
                         (date-time 2013 2 17) [{:time 16} {:time 31}]}]

    (is (= {(date-time 2013 2 16) 45} (dates-and-worked-hours dates dates-and-tasks))))


  (let [dates [(date-time 2013 2 16) (date-time 2013 2 17)]
        dates-and-tasks {(date-time 2013 2 16) [{:time 15} {:time 30}]
                         (date-time 2013 2 17) [{:time 16} {:time 31}]}]

    (is (= {(date-time 2013 2 16) 45, (date-time 2013 2 17) 47} (dates-and-worked-hours dates dates-and-tasks))))


  (let [dates [(date-time 2013 2 16) (date-time 2013 2 17)]
        dates-and-tasks {}]

    (is (= {(date-time 2013 2 16) 0, (date-time 2013 2 17) 0} (dates-and-worked-hours dates dates-and-tasks))   ))

  )
