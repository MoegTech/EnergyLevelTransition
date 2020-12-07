Other Languages: [English](README.md) | [Русский](README.RU.md)  | [简体中文](README.CN.md)

# 能级跃迁(Energy Level Transition)

![构建状态](https://github.com/MoegTech/EnergyLevelTransition/workflows/Build/badge.svg) 
[![Discord - https://discord.gg/BWn6E94](https://img.shields.io/badge/Discord-Join%20Us-blue)](https://discord.gg/BWn6E94)
[![QQ群 - 940209097](https://img.shields.io/badge/QQ%E7%BE%A4-940209097-blue)](https://jq.qq.com/?_wv=1027&k=keVW7jBX)

![LOGO](https://raw.githubusercontent.com/MoegTech/EnergyLevelTransition/1.16/src/main/resources/logos/logo-300-300.png)

这是 Minecraft 模组 - 能级跃迁(Energy Level Transition)的官方仓库，适用于 Minecraft 1.16+ .

- [能级跃迁](#energy-level-transition)
- [导言](#导言)
- [背景](#背景)
- [协议](#协议)
- [致谢](#致谢)
- [依赖](#依赖)
- [设置开发环境](#设置开发环境)

## 导言

WIP

## 背景

能级跃迁模组从 Lyuuke(https://github.com/Lyuuke) 创造的脑洞贴中获取部分灵感
- ELT II: https://tieba.baidu.com/p/3491285047
- ELT IIS: https://tieba.baidu.com/p/5858060400
- ELT Wiki: https://energy-level-transition.fandom.com/zh/wiki/Fraxinus_In_A_Gale

## 协议

本模组的所有模块的Mod-ID以 `elt` 开头的均使用 [GNU General Public License Version 3](LICENSE) 授权。

除非另有说明，所有的资源文件
均根据 [ELT 资产许可证](src/main/resources/LICENSE.assets) 进行许可。

任何包含 [ELT 徽标](src/main/resources/assets.energyleveltransition/icon.png) 的资源文件及任何衍生品
均根据协议 [CC-BY-NC 4.0](src/main/resources/LICENSE.logos) 进行许可。

## 致谢

[Lyuuke](https://github.com/Lyuuke)，为这个模组贡献了图像等资源文件和他的创意.

## 依赖

TBA

## 设置开发环境

如果你在使用 Windows，使用以下命令行运行 Gradle:

```gradlew <task>```

或者你在使用 Linux/Unix/MacOS (Unix like):

```./gradlew <task>```

1. 首先，在根目录下执行`git clone https://github.com/MoegTech/EnergyLevelTransition.git`克隆本项目。

2. 将你的工作目录重命名为`ELT_Workspace`（可以任意命名），我们称之为根目录。

3. 然后，用 IntelliJ IDEA 或 Eclipse 打开根目录下的`build.gradle`文件，选择`import as project`。

4. 如果执行成功, 你就可以继续下一步，恭喜。

5. 在根目录下运行`gradlew genIntellijRuns` 或者 `gradlew genEclipseRuns`，生成必要的文件。

6. 在根目录下运行`gradlew build`，在目录 `./ELT_Workspace/build/libs` 下可以找到构建产物。

7. 运行 `gradlew runClient` 来运行包含有 ELT 的 Minecraft 客户端。

对于更多的设置说明或遇到的问题，请检查 [Forge文档](https://mcforge.readthedocs.io/en/1.16.x/gettingstarted/)，涉及到你正在使用的IDE。