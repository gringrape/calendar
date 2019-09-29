<template>
  <div class="tab-content">
    <div class="tab-pane active show" id="month" role="tabpanel" aria-labelledby="tab-month">
      <div class="monthly-calendar">
        <div class="week-day">
          <div class="day-name">일</div>
          <div class="day-name">월</div>
          <div class="day-name">화</div>
          <div class="day-name">수</div>
          <div class="day-name">목</div>
          <div class="day-name">금</div>
          <div class="day-name">토</div>
        </div>
        <div class="week" v-for="week in weeks">
          <div class="day" v-for="day in week">
            <h3 class="day-label">{{ day }}</h3>         
          </div>
        </div>
      </div>
    </div>
  </div>    
</template>

<script>
import moment from 'moment'

export default {
  name: 'CalendarContents',
  props: {
    yearCal: String,
    monthCal: String
  },
  data() {
    return {
      weeks: []
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
        week.push(curr.format('D'))

        if(week.length == 7) {
          this.weeks.push(week)
          week = []
        }

        if(curr.format('MMDD') == end.format('MMDD')) {
          return
        }

        curr = curr.add(1, 'days')
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

