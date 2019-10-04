<template>
<div v-if="show">
  <transition name="modal">
  <div class="modal-mask">
    <div class="modal-wrapper">

  <div id="registerSchedule" tabindex="-1" role="dialog" aria-labelledby="registerScheduleLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="registerScheduleLabel">일정 만들기</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="close()">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label class="col-form-label">일정 제목</label>
                        <input type="text" class="form-control" id="recipient-name" v-model="title">
                        <h5 v-if="!checkers.title" class="checkMessage">제목을 입력해주세요</h5>
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">일정 설명</label>
                        <textarea class="form-control" id="message-text" v-model="description"></textarea>
                        <h5 v-if="!checkers.description" class="checkMessage">설명을 입력해주세요</h5>
                    </div>
                    <div class="form-row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-form-label">일정 시작 날짜</label>
                                <div class="input-group date" id="datetimepicker1" data-target-input="nearest">
                                    <input type="text" class="form-control datetimepicker-input"
                                           data-target="#datetimepicker1" v-model="startDay" :disabled="true"/>
                                    <div class="input-group-append" data-target="#datetimepicker1"
                                         data-toggle="datetimepicker" v-popover:picker.top @click="setStart()">
                                        <div class="input-group-text"><i class="material-icons">
                                            calendar_today
                                        </i></div>
                                    </div>
                                </div>
                                <h5 v-if="!checkers.startDay" class="checkMessage">시작일을 입력해주세요</h5>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-form-label">일정 시작 시간</label>
                                <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
                                    <input type="text" class="form-control datetimepicker-input"
                                           data-target="#datetimepicker2" v-model="startTime" :disabled="true"/>
                                    <div class="input-group-append" data-target="#datetimepicker2"
                                         data-toggle="datetimepicker" v-popover:timePicker.left @click="setTimeStartTarget()">
                                        <div class="input-group-text"><i class="material-icons">
                                            access_time
                                        </i></div>
                                    </div>
                                </div>
                                <h5 v-if="!checkers.startTime" class="checkMessage">시작시간을 입력해주세요</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-form-label">일정 종료 날짜</label>
                                <div class="input-group date" id="datetimepicker3" data-target-input="nearest">
                                    <input type="text" class="form-control datetimepicker-input"
                                           data-target="#datetimepicker3" v-model="endDay" :disabled="true"/>
                                    <div class="input-group-append" data-target="#datetimepicker3"
                                         data-toggle="datetimepicker" v-popover:picker.top @click="setEnd()">
                                        <div class="input-group-text"><i class="material-icons">
                                            calendar_today
                                        </i></div>
                                    </div>
                                </div>
                                <h5 v-if="!checkers.endDay" class="checkMessage">종료일을 입력해주세요</h5>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-form-label">일정 종료 시간</label>
                                <div class="input-group date" id="datetimepicker4" data-target-input="nearest">
                                    <input type="text" class="form-control datetimepicker-input"
                                           data-target="#datetimepicker4" v-model="endTime" :disabled="true"/>
                                    <div class="input-group-append" data-target="# datetimepicker4"
                                         data-toggle="datetimepicker" v-popover:timePicker.left @click="setTimeEndTarget()">
                                        <div class="input-group-text"><i class="material-icons">
                                            access_time
                                        </i></div>
                                    </div>
                                </div>
                            <h5 v-if="!checkers.endDay" class="checkMessage">종료시간을 입력해주세요</h5>
                            </div>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="inlineCheckbox1" value="option1">
                            <label class="form-check-label" for="inlineCheckbox1">매월 반복</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="inlineCheckbox2" v-model="alldayChecked">
                            <label class="form-check-label" for="inlineCheckbox2">하루 종일 </label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button @click="send()">일정 만들기</button>
                <button @click="cancel()">취소</button>
            </div>
            <popover name="picker">
              <div>
                <datepicker :inline="true" @selected="setDate" :disabledDates="disabledDates"></datepicker>
              </div>
            </popover>
            <popover :pointer="false" name="timePicker">
              <div>
                <vue-timepicker @change="changeHandler"></vue-timepicker>
              </div>
            </popover>
        </div>
    </div>
</div>
</div>
</div>
</transition>
</div>
</template>

<script>
import moment from 'moment';
import axios from 'axios';
import Datepicker from 'vuejs-datepicker';
import VueTimepicker from 'vue2-timepicker';
import router from '../router';

export default {
  name: 'RegisterForm',
  props: {
    checker: {
      type: Boolean,
      default: true
    },
    pickedStartDate: String
  },
  components: {
    Datepicker,
    VueTimepicker
  },
  data() {
    return {
      // for element
      show: false,
      title: '',
      description: '',
      repetitionPeriod: 0,
      msg: {},
      // for date picker
      pickerTarget: '',
      startDay: '',
      endDay: '',
      disabledDates: {
        to: new Date(Date.now() - 8640000)
      },
      // for time picker
      timePickerTarget: '',
      startTime: '',
      endTime: '',
      // checking
      alldayChecked: false
    }
  },
  methods: {
    send: function() {
      for(var key in this.checkers) {
        if(this.checkers[key] == false) {
          return
        }
      }

      axios.post('/itineraries', {
        title: this.title,
        description: this.description,
        startDate: this.startDay + this.startTime,
        endDate: this.endDay + this.endTime,
        repetitionPeriod: this.repetitionPeriod
      })
      .then(function (response) {
        console.log(response);
        router.go();
      })
      .catch(function (error) {
        console.log(error);
      });
      this.cancel();
      this.show = false;
    },
    cancel: function() {
      this.title =''
      this.description =''
      this.startDay =''
      this.endDay =''
      this.startTime =''
      this.endTime =''
    },
    hide: function() {
      this.show = false
    },
    close: function() {
      this.cancel()
      this.hide()
    },
    // date picker
    setStart: function() {
      this.pickerTarget = "startDate";
      this.disabledDates.to = new Date(Date.now() - 8640000);
    },
    setEnd: function() {
      this.pickerTarget = "endDate"
    },
    setDate(value) {
      if(this.pickerTarget == "startDate") {
        this.startDay = moment(value).format('YYYYMMDD')
      } else {
        this.endDay = moment(value).format('YYYYMMDD')
      }
    },
    // time picker
    setTimeStartTarget: function() {
      this.timePickerTarget = "startTime";
    },
    setTimeEndTarget: function() {
      this.timePickerTarget = "endTime";
    },
    changeHandler(eventData) {
      var eventTime = eventData.data['HH'] + eventData.data['mm']
      if(this.timePickerTarget == "startTime") {
        this.startTime = eventTime
      } else {
        this.endTime = eventTime
      }
    }
  },
  watch: {
    checker: function() {
      this.show = true
    },
    startDay: function() {
      // validation startday < endday
      var startMoment = moment(this.startDay, "YYYYMMDD")
      var endMoment = moment(this.endDay, "YYYYMMDD")
      if(endMoment.isAfter(startMoment)) {
        this.endDay = ''
      }

      // limit endday
      var startMoment = moment(this.startDay, 'YYYYMMDD');
      console.log(startMoment)
      this.disabledDates.to = new Date(parseInt(startMoment.format('YYYY'))
      , parseInt(startMoment.format('M') - 1)
      , parseInt(startMoment.format('D')));
      console.log(this.disabledDates.to)
    },
    endTime: function() {
      if(this.startDay != '' && 
      this.endDay != '' && 
      this.startTime != '' &&
      this.startDay == this.endDay) {
        var startMoment = moment(this.startDay + this.startTime, "YYYYMMDDHHmm")
        var endMoment = moment(this.endDay + this.endTime, "YYYYMMDDHHmm")
        if(startMoment.isAfter(endMoment)) {
          this.endTime = ''
        } 
      }
    },
    alldayChecked: function() {
      if(this.alldayChecked) {
        this.startTime = '0000'
        this.endTime = '2359'
      } else {
        this.startTime = ''
        this.endTime = ''
      }
    },
    pickedStartDate: function() {
      var pickedMoment = moment(this.pickedStartDate, "YYYYMMDD")
      if (moment().startOf('day')
            .isAfter(pickedMoment.startOf('day'))) {
              this.startDay = moment().format('YYYYMMDD')
            } else {
              this.startDay = this.pickedStartDate
            }
      
    }
  },
  computed: {
    checkers: function() {
      var result = {}
      if(this.title == '') {
        result.title = false
      } else {
        result.title = true
      }
      if(this.description == '') {
        result.description = false
      } else {
        result.description = true
      }
      if(this.startDay == '') {
        result.startDay = false
      } else {
        result.startDay = true
      }
      if(this.startTime == '') {
        result.startTime = false
      } else {
        result.startTime = true
      }
      if(this.endDay == '') {
        result.endDay = false
      } else {
        result.endDay = true
      }
      if(this.endTime == '') {
        result.endTime = false
      } else {
        result.endTime = true
      }
      return result
    }
  }
}
</script>

<style>
.checkMessage {
  margin-top: 5px;
  font-size: 10px;
  color: red;
  font-weight: 400;
}

.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .5);
  display: table;
  transition: opacity .3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

div[data-popover="picker"] {
  width: 500px;
  padding-right: 305px;
}

div[data-popover="timePicker"] {
  padding-bottom: 170px;
  padding-right: -5px;
}

</style>