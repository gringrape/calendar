<template>
  <div class="tab-content">
    <div class="tab-pane active show" id="month" role="tabpanel" aria-labelledby="tab-month">
      <div class="monthly-calendar">
        <div class="week-day">
          <div class="day-name" v-for="name in dayName" :key="name.toString()">{{ name }}</div>
        </div>
        <div class="week" v-for="week in weeks" :key="week.toString()" @click="switching()">
          <div class="day" v-for="date in week" :key="date.day.toString()" ref="days">
            <h3 v-show="false">{{ date["month"] }}</h3>
            <h3 class="day-label">{{ date["day"] }}</h3>
          </div>
        </div>
      </div>
    </div>
    <button @click="getList()">
      일정 불러오기 연습
    </button>
    <button @click="drawItineraries()">
      일정 그리기 연습
    </button>
    <h1>{{ test }}</h1>
    <!-- <h5 v-for="ele in itineraries">{{ ele }}</h5> -->
    <h5 v-for="el in sep" >{{ el }}</h5>
    <RegisterForm :checker="switcher">
    </RegisterForm>
  </div>    
</template>

<script>
import axios from 'axios'
import Vue from 'vue'
import moment from 'moment'
import Ordinary from '@/components/Ordinary'
import Consecutive from '@/components/Consecutive'
import RegisterForm from '@/components/RegisterForm'

export default {
  name: 'CalendarContents',
  props: {
    yearCal: String,
    monthCal: String
  },
  components: {
    RegisterForm
  },
  data() {
    return {
      switcher: false,
      dayName: ['일', '월', '화', '수', '목', '금', '토'],
      weeks: [],
      itineraries: [],
      sep: [],
      test: ''
    }
  },
  methods: {
    calStart: function() {
      var year = this.yearCal
      var month = this.monthCal

      if (month.length == 1) {
        month = "0" + month
      }

      var fisrtDay = moment(year + month + "01", "YYYYMMDD");

      if(fisrtDay.format('dddd') == 'Sunday') {
        return fisrtDay
      }

      return fisrtDay.day(0)
    },
    calEnd: function() {
      var year = this.yearCal
      var month = this.monthCal

      if (month.length == 1) {
        month = "0" + month
      }

      var lastDay = moment(year + month + "01", "YYYYMMDD").endOf('month');

      if(lastDay.format('dddd') == 'Saturday') {
        return lastDay
      }

      return lastDay.day(6)
    
    },
    getCalendar: function() {
      var week = []
      var curr = this.calStart()
      var end = this.calEnd()

      this.weeks = []

      while(true) {
        var dateEl = {} 
        dateEl.month = curr.format('M')
        dateEl.day = curr.format('D')
        week.push(dateEl)

        if(week.length == 7) {
          this.weeks.push(week)
          week = []
        }

        if(curr.format('MMDD') == end.format('MMDD')) {
          return
        }

        curr = curr.add(1, 'days')
      }
    },
    insert: function(separatedItinerary) {

        var targets = this.$refs.days
        var candidate;
        var target;

        var startDate = moment(separatedItinerary["startDate"], "YYYYMMDDHHmm")
        var endDate = moment(separatedItinerary["endDate"], "YYYYMMDDHHmm")

        for(candidate of targets) {
          var dayValue = candidate.children[1].innerHTML
          var monthValue = candidate.children[0].innerHTML
          if(dayValue == startDate.format('D') &&
              monthValue == startDate.format('M')) {
            target = candidate
            break
          }
        }

        var diff = endDate.diff(startDate, 'days') + 1;
        var sort = separatedItinerary["sort"]

        if(sort == 1) {
          var ComponentClass = Vue.extend(Ordinary)
        } else if(sort == 2) {
          var ComponentClass = Vue.extend(Consecutive)
        } else {
          // 반복 일정
        }
          
        var instance = new ComponentClass({
          propsData: {
            id: "" + separatedItinerary["id"],
            title: separatedItinerary["title"],
            period: diff
          }
        })

        instance.$mount() // pass nothing
        target.appendChild(instance.$el)

    },
    getList: function() {
      var startMoment = this.calStart()
      var endMoment = this.calEnd()

      var startDate = startMoment.format('YYYY') +
                      startMoment.format('MM') +
                      startMoment.format('DD')

      var endDate = endMoment.format('YYYY') +
                      endMoment.format('MM') +
                      endMoment.format('DD')

      axios.get('http://localhost:5000/itineraries?startDate=' + startDate + '&endDate=' + endDate)
      .then(response => this.itineraries = response.data)
    },
    drawItineraries: function() {
      var itineraries = this.itineraries

      var separatedItineraries = []

      var itinerary = {}

      for(itinerary of itineraries) {
        this.separate(separatedItineraries, itinerary)
      }
      this.sep = separatedItineraries;

      var separatedItinerary;

      for(separatedItinerary of separatedItineraries) {
        this.insert(separatedItinerary)
      }
    },
    separate: function(separatedItineraries, itinerary) {
      // todo: 일주일을 넘는 일정을 분리해서 분리된 일정목록에 넣어주기 -> 가장 어려운 작업....
        var start = moment(itinerary["startDate"], "YYYYMMDDHHmm")
        var end = moment(itinerary["endDate"], "YYYYMMDDHHmm")

        // 이전 월에 걸친 일정 - o.k.
        if(this.calStart().isAfter(start)) {
          var newStart = this.calStart()
          itinerary["startDate"] = newStart.format('YYYY') +
                                 newStart.format('MM') +
                                 newStart.format('DD') +
                                 newStart.format("HH") +
                                 newStart.format("mm")
          this.separate(separatedItineraries, itinerary)
          return
        }

        // 다음 월에 걸친 일정 - o.k.
        if(end.isAfter(this.calEnd())) {
          var newEnd = this.calEnd()
          itinerary["endDate"] = newEnd.format('YYYY') +
                                 newEnd.format('MM') +
                                 newEnd.format('DD') +
                                 newEnd.format("HH") +
                                 newEnd.format("mm")
          separatedItineraries.push(itinerary)
          return
        } 
        
        // 한주 단위를 넘는 일정 -> deep copy 가 핵심......!!!
        if(end.isAfter(start.day(6))) {          
          var leftover = JSON.parse(JSON.stringify(itinerary)); // deep copy
          var newEnd = start.day(6)
          itinerary["endDate"] = newEnd.format('YYYY') +
                                 newEnd.format('MM') +
                                 newEnd.format('DD') +
                                 newEnd.format("HH") +
                                 newEnd.format("mm")
          separatedItineraries.push(itinerary)

          var newStart = start.day(7)
          leftover["startDate"] = newStart.format('YYYY') +
                                 newStart.format('MM') +
                                 newStart.format('DD') +
                                 newStart.format("HH") +
                                 newStart.format("mm")

          this.separate(separatedItineraries, leftover)
        } else {
          separatedItineraries.push(itinerary)
        }
    },
    switching: function() {
      if(this.switcher == true) {
        this.switcher = false
      } else {
        this.switcher = true
      }
    }
  },
  watch: {
    monthCal: function() {
      this.getCalendar();
    }
  }
}
</script>

<style>
</style>

