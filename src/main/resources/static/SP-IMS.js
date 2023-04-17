var v1 = new Vue({
  el: "#app1",
  methods: {
    handleClick(row) {
      //console.log(row);
    },
    changeArrToStr(arr) {
      let newArr = [];
      arr.forEach((item) => newArr.push(item.name));
      return newArr.join(",");
    },
    pageChange(pageNum) {
      //console.log(pageNum);
      if (pageNum <= 0) {
        return;
      } else {
        this.getData(pageNum);
      }
    },

    async getData(pageNum) {
      const { data: res } = await axios({
        method: "get",
        url: "/api/program",
        params: {
          pageNum,
        },
        headers: {
          "content-type": "application/json",
        },
      });
      //console.log(res.data.programResults)
      // res.data.forEach((item) => {
      //   item.actorList = this.changeArrToStr(item.actorList);
      // });

      this.tableData = res.data;
      //console.log(this.tableData)
    },

    async search(searchForm) {
      const { data: res } = await axios({
        method: "get",
        url: "/api/search",
        params: {
          type: searchForm.typeName,
          num: searchForm.actorNum,
          name: searchForm.name
        },
        headers: {
          "content-type": "application/json",
        },
      });
      //console.log(res)
      // res.data.forEach((item) => {
      //   item.actorList = this.changeArrToStr(item.actorList);
      // });

      this.tableData = res.data;
      //console.log(res.data.programSearchResults)
      //console.log(this.tableData)
    },

    add() {
      this.isAdd = true
    },
    addConfirm() {
      this.isAdd = false
      this.addData(this.addForm)
    },
    async addData(addForm) {
      const { data: res } = await axios({
        method: "post",
        url: "/add/program",
        data: {
          typeName: addForm.typeName,
          name: addForm.name,
          actorList: addForm.actorList,
          view: addForm.view
        },
        headers: {
          "content-type": "application/json",
        },
      });
      //console.log(res)
      if (res.code == '0') {
        this.getData(this.pageNum);
        this.$message({
          message: '新增成功',
          type: 'success'
        });
      } else {
        this.$message.error(res.message);
      }

    },

    edit(row) {
      this.editForm.id = row.id
      this.editForm.typeName = row.typeName
      this.editForm.name = row.name
      this.editForm.actorList = row.actorList
      this.editForm.view = row.view
      this.isEdit = true
    },
    editConfirm() {
      this.isEdit = false,
        this.editData(this.editForm)
    },
    async editData(editForm) {
      const { data: res } = await axios({
        method: "post",
        url: "/update/program",
        data: {
          id: editForm.id,
          typeName: editForm.typeName,
          name: editForm.name,
          actorList: editForm.actorList,
          view: editForm.view
        },
        headers: {
          "content-type": "application/json",
        },
      });
      //console.log(res)
      if (res.code == '0') {
        this.getData(this.pageNum);
        this.$message({
          message: '编辑成功',
          type: 'success'
        });
      } else {
        this.$message.error(res.message);
      }

    },

    async deleteData(index) {
      const { data: res } = await axios({
        method: "post",
        url: "/delete/program",
        data: {
          id: index
        },
        headers: {
          "content-type": "application/json",
        },
      });
      //console.log(res)
      if (res.code == '0') {
        this.getData(this.pageNum);
        this.$message({
          message: '删除成功',
          type: 'success'
        });
      } else {
        this.$message.error(res.message);
      }
    },
  },

  data() {
    return {
      cateOptions: [{
        value: '歌舞',
        label: '歌舞'
      }, {
        value: '戏曲',
        label: '戏曲'
      }, {
        value: '小品',
        label: '小品'
      }, {
        value: '相声',
        label: '相声'
      }, {
        value: '武术',
        label: '武术'
      }, {
        value: '其他',
        label: '其他'
      }],
      addForm: {
        typeName: null,
        name: null,
        actorList: null,
        view: null
      },
      searchForm: {
        typeName: null,
        actorNum: null,
        name: null,
      },
      editForm: {
        typeName: null,
        name: null,
        actorList: null,
        view: null
      },
      value: '',
      isEdit: false,
      isAdd: false,
      tableData: [],
      pageNum: 1,
    };
  },

  created() {
    this.getData(this.pageNum);
  },
});
