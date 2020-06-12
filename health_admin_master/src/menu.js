const menus = [{
    action: "mdi-home",
    title: "首页",
    path: "/index",
    items: [{
      title: "统计",
      path: "/dashboard"
    }]
  },
  {
    action: "mdi-account-settings",
    title: "会员管理",
    path: "/user",
    items: [{
        action: "mdi-account-edit",
        title: "用户管理",
        path: "/usermanagement"
      },
      {
        action: "mdi-account-alert",
        title: "体检管理",
        path: "/exam"
      }
    ]
  },
  {
    action: "mdi-apps",
    title: "网站管理",
    path: "/item",
    items: [{
        title: "新闻管理",
        path: "/news"
      },
      {
        title: "工具管理",
        path: "/tool"
      },
      {
        title: "分类管理",
        path: "/category"
      }
    ]
  }
]

export default menus;
