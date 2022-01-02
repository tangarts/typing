(ns typing.components.style)

(def word-css
  {:font-size 18
   :letter-spacing "0.06ex"
   ;:line-height "1.8em"
   :font-family "Source Code Pro, monospace"
   ; :font-family "Courier Prime Sans Code, Consolas, Liberation Mono, Courier, monospace"
   :overflow "hidden"
   :margin "0 auto"
   })

(def root
  {:font-family "sans-serif"
   :width "100%"
   :height "100%"
   :min-height "100vh"
   :background "#f9f9f9"
   :display "flex"
   :flex-direction "column"
   :justify-content "center"
   :align-items "center"
   })


(def container
  {:margin "8px auto"
   :max-width 750
   :width "90%"
   })

(def board 
  {:margin-bottom "15px"
   :overflow "hidden"
   ; :background-color "#fff"
   ; :border-radius 6
   ; :box-shadow "2px 4px 2px #808080"
   :min-height "240px"
   })

(def statistics
  {:padding "12px 16px"
   :display "flex"
   :flex-direction "row"
   :background "#444"
   :color "#fff"
   :align-items "center"
   :font-size 12
   :border-radius 6
   :box-shadow "1px 2px 1px #808080"
   })

(def inputs
  {:user-select "none"
   :overflow "hidden"
   })

(def button
  {:background "transparent" ;
   :border "none";
   :color "inherit"
   })
