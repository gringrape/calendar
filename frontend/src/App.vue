<template>
  <div class="calendar-container">
      <div class="calendar-header">
          <button class="btn btn-light">Today</button>
          <div class="ico-arrow">
              <a href="#" data-toggle="tooltip" data-placement="top" title="이전">
                  <i class="material-icons" @click="getPrev()">
                    arrow_left
                  </i>
              </a>
              <a href="#" data-toggle="tooltip" data-placement="top" title="다음">
                  <i class="material-icons" @click="getNext()">
                    arrow_right
                  </i>
              </a>
          </div>
          <h4>{{year}}년 {{month}}월</h4>
          <ul class="nav nav-tabs view-tab" id="view" role="tablist">
              <li class="nav-item">
                  <a class="nav-link active" id="tab-month" data-toggle="tab" href="#month" role="tab"
                    aria-controls="month"
                    aria-selected="true" 
                    @click="status = 'month'">
                      월
                  </a>
              </li>
              <li class="nav-item">
                  <a class="nav-link" id="tab-day" data-toggle="tab" href="#day" role="tab" 
                      aria-controls="day"
                      aria-selected="false"
                      @click="status = 'day'">
                        일
                  </a>
              </li>
          </ul>
      </div>
      <div class="tab-content">
        <router-view 
          v-if="this.status == 'month'" 
          name="monthlyView" 
          :yearCal="year" 
          :monthCal="month" 
          :counter="componentKey" 
          :key="componentKey" 
          @registered="forceUpdateRegistered()">
        </router-view>
        <router-view 
          v-if="this.status == 'day'" 
          name="dailyView"
          :yearCal="year" 
          :monthCal="month"
          :dayCal="day"
          :weekdayCal="weekday"
          :key="componentKey" 
          >
        </router-view>
      </div>
  </div>
</template>

<script>
import Vue from 'vue'
import moment from 'moment'

export default {
  name: 'App',
  data() {
    return {
      status: 'month',
      year: '',
      month: '',
      // for daily view
      day: '',
      weekday: '',
      // for update
      componentKey: 0,
      map: {
        "Monday":"월요일",
        "Tuesday":"화요일",
        "Wednesday":"수요일",
        "Thursday":"목요일",
        "Friday":"금요일",
        "Saturday":"토요일",
        "Sunday":"일요일"
      } 
    }
  },
  methods: {
    initialize: function() {
      this.year = moment().format('YYYY')
      this.month = moment().format('M')
      this.day = moment().format('D')
      this.weekday = this.map[moment().format('dddd')]
    },
    getPrev:function() {
      if(this.status == 'month') {
        this.getPrevMonth()
      } else {
        this.getPrevDay()
      }
    },
    getNext: function() {
      if(this.status == 'month') {
        this.getNextMonth()
      } else {
        this.getNextDay()
      }
    },
    getPrevMonth: function() {
      var curr = moment(this.year + this.month, 'YYYYM')
      var prev = curr.subtract(1, 'months')
      this.year = prev.format('YYYY')
      this.month = prev.format('M')
      this.day = prev.startOf('month').format('D')
      this.weekday = this.map[prev.startOf('month').format('dddd')]
      this.forceUpdate();
    },
    getNextMonth: function() {
      var curr = moment(this.year + this.month, 'YYYYM')
      var next = curr.add(1, 'months')
      this.year = next.format('YYYY')
      this.month = next.format('M')
      this.day = next.startOf('month').format('D')
      this.weekday = this.map[next.startOf('month').format('dddd')]
      this.forceUpdate();
    },
    getPrevDay: function() {
      if(this.month.length == 1) {
        this.month = '0' + this.month
      }
      var curr = moment(this.year + this.month + this.day, 'YYYYMMD')
      var prev = curr.subtract(1, 'days')
      this.year = prev.format('YYYY')
      this.month = prev.format('M')
      this.day = prev.format('D')
      this.weekday = this.map[prev.format('dddd')]
      this.forceUpdate();
    },
    getNextDay: function() {
      if(this.month.length == 1) {
        this.month = '0' + this.month
      }
      var curr = moment(this.year + this.month + this.day, 'YYYYMMD')
      var next = curr.add(1, 'days')
      this.year = next.format('YYYY')
      this.month = next.format('M')
      this.day = next.format('D')
      this.weekday = this.map[next.format('dddd')]
      this.forceUpdate();
    },
    forceUpdate: function() {
      this.componentKey += 1;
      console.log("업데이트~!!!")
    }
  },
  mounted() {
    this.initialize()
  }
}
</script>

<style>
@import url("https://fonts.googleapis.com/icon?family=Material+Icons"); 
@import "assets/css/basic.css";
@import 'vue2-timepicker/dist/VueTimepicker.css'
</style>
