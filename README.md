# PetLink（Jetpack Compose + 高德地图）

## 1. 运行前必做：配置高德 Key

在高德开放平台创建 Android Key 后，把 Key 写到项目根目录的 `local.properties`：

```properties
# 你的 Android SDK 路径（AS 会自动写）
sdk.dir=/path/to/Android/Sdk

# 高德 Key
AMAP_API_KEY=替换为你的Key
```

> 项目通过 `manifestPlaceholders` 把 `AMAP_API_KEY` 注入到 Manifest 的 `com.amap.api.v2.apikey`。

## 2. 已包含的业务骨架
- 宠物档案（新增/列表）
- 附近页：嵌入高德地图（演示）
- 地图选点页：点击地图落点（演示）
- 遛狗：开始/结束 + 模拟距离/卡路里（后续替换真实定位轨迹）
- 地点排行榜：按地点聚合本地遛狗数据
- 聊天：UI 占位（后续接入后端）

## 3. 后续接入建议（你们产品推进用）
1) 账号体系（短信/微信）
2) 真实定位轨迹记录（AMapLocationClient 或 FusedLocation）
3) 轨迹 → 距离/强度 → 卡路里
4) 榜单维度（日/周/月）+ 反作弊（速度阈值、漂移过滤）
5) 附近推荐（同小区/同时间/路线重合）
