<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover" style="height:252px;">
          <div slot="header" class="clearfix">
            <span>CPU</span>
          </div>
          线程数：{{ cpu.cpuNum }} <br>
          总数：{{ cpu.total }} <br>
          系统使用数：{{ cpu.sys }}<br>
          用户使用数：{{ cpu.used }}<br>
          剩余数：{{ cpu.free }}<br>
          使用率：{{ gteProgress(cpu.used, cpu.total) }}
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card shadow="hover" style="height:252px;">
          <div slot="header" class="clearfix">
            <span>内存</span>
          </div>
          总量：{{ Math.round(100 * info.mem.total / 1024 / 1024 / 1024) / 100 }}GB<br>
          使用量：{{ Math.round(100 * info.mem.used / 1024 / 1024 / 1024) / 100 }}GB<br>
          剩余量：{{ Math.round(100 * info.mem.free / 1024 / 1024 / 1024) / 100 }}GB<br>
          使用率：{{ info.mem.usage }}%
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card shadow="hover" style="height:252px;">
          <div slot="header" class="clearfix">
            <span>JVM</span>
          </div>
          总量：{{ Math.round(100 * info.jvm.total / 1024 / 1024 / 1024) / 100 }}GB<br>
          最大量：{{ Math.round(100 * info.jvm.max / 1024 / 1024 / 1024) / 100 }}GB<br>
          剩余量：{{ Math.round(100 * info.jvm.free / 1024 / 1024 / 1024) / 100 }}GB<br>
          版本：{{ info.jvm.version }}<br>
          目录：{{ info.jvm.home }}<br>
          开始：{{ info.jvm.startTime }}<br>
          运行时长：{{ info.jvm.runTime }}<br>
        </el-card>
      </el-col>
      <el-col :span="24">
        <el-card shadow="hover" style="margin-top: 30px;">
          <div slot="header" class="clearfix">
            <span>{{ info.net.hostName }}</span>
          </div>
          <el-table :data="info.net.nets">
            <el-table-column prop="name" label="网卡名" width="550"></el-table-column>
            <el-table-column prop="mac" label="Mac"></el-table-column>
            <el-table-column prop="ipv4" label="IPV4"></el-table-column>
            <el-table-column prop="ipv6" label="IPV6"></el-table-column>
            <el-table-column prop="speed" label="带宽"></el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="24">
        <el-card shadow="hover" style="margin-top: 30px;">
          <div slot="header" class="clearfix">
            <span>硬盘</span>
          </div>
          <el-table :data="info.files">
            <el-table-column prop="dirName" label="命名"></el-table-column>
            <el-table-column prop="sysTypeName" label="格式"></el-table-column>
            <el-table-column prop="typeName" label="类型"></el-table-column>
            <el-table-column prop="total" label="大小"></el-table-column>
            <el-table-column prop="free" label="可用"></el-table-column>
            <el-table-column prop="used" label="已用"></el-table-column>
            <el-table-column prop="usage" label="使用率" width="300">
              <template v-slot="scope">
                <el-progress :percentage="scope.row.usage"></el-progress>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>

<script>
import {LoginUserInfo} from '../../utils/Api'

export default {
  name: '信息',
  data() {
    return {
      info: {},
      cpu:{}
    };
  },
  components: {},
  computed: {},
  created() {
    this.getInfo();
  },
  methods: {
    getInfo() {
      LoginUserInfo().then(res => {
        if (res.code === 200) {
          this.info = res.content;
          this.cpu = res.content.cpu;
        } else {
          this.$message.error('查看失败,' + res.msg);
        }
      });
    },
    // 服务进度的转换
    gteProgress(val1, val2) {
      if (val1 == 0 || val2 == 0) {
        return 0
      }
      return (Math.round(val1 / val2 * 10000) / 100.00) + "%";
    },
  }
};
</script>


<style scoped>

.grid-con-1 .grid-con-icon {
  background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
  background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
  background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
  color: rgb(242, 94, 67);
}

.user-info-cont div:first-child {
  font-size: 30px;
  color: #222;
}

.user-info-list span {
  margin-left: 70px;
}

</style>
