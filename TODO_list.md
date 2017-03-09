## 存在的问题
1. 三星、酷派手机的兼容问题
2. 依赖太旧的问题->选择去除依赖还是更新依赖  done 以去除依赖
3. 自动刷新动画生硬  todo 问题待验证
4. 加载更多闪烁问题 done 做了优化
5. layout_behavior支持问题 添加了Demo但未完成任何逻辑处理
6. 是否要支持ViewPager回弹问题 TODO
7. 是否要支持所有的View TODO  不能滑动NormalView是因为没有焦点的原因（需手动设置）
8. 测试事件监听冲突问题 理论上修改后已经不存在这个问题
9. 内存泄漏问题  -> 解决ValueAnimator潜在的内存泄漏问题；WebView内存泄漏问题；done
10. 仿QQ视差效果
11. 测试加载更多后不添加数据  done
12. 考虑是否要给Loadmore添加完成延时
13. FixedHeader问题
14. 多点触摸处理
15. 控制底部下拉后或者顶部上拉后再次进入动画可以保持！

## 新发现的问题
1. beizierlayout主动调用刷新时会一片白
2. BallPulseView引入了内存泄漏 done
3. 新的方案，怎么让scroll更平滑；计算Footer降低与TargetView显示距离是否一致：结论，一致，问题在每次滚动的距离上  done
4. requestLayout时提示 **improperly called by android.support.v7.widget.AppCompatTextView**
5. WebView上拉不起作用

