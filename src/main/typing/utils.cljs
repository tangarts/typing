(ns typing.utils
  (:require
    [reagent.core :as r]
    [stopwatch.core :as stopwatch]
    [clojure.string :as string]))

(comment 
  ; code for random stuff
  ; and utils like timer
)

;; Compatability
;; Must extend number for js/Number since time is represented as milliseconds


(def cpm (r/atom 0))

(defn get-cpm
  [characters time]
  (let [time-elapsed time]
    (/ (Math/floor (* characters 60)) time-elapsed)))


