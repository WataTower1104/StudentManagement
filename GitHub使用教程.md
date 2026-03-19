# GitHub使用教程 - 新手入门完全指南

## 一、GitHub是什么？

GitHub是一个基于Git的代码托管平台，用于版本控制和协作开发。简单来说，它就是一个"代码的百度网盘"，但功能更强大：

- **代码托管**：存放你的项目代码
- **版本控制**：记录代码的每一次修改
- **团队协作**：多人共同开发项目
- **开源社区**：分享和学习优秀代码

## 二、准备工作

### 1. 创建GitHub账号

1. 访问 [GitHub官网](https://github.com)
2. 点击"Sign up"注册账号
3. 填写邮箱、密码、用户名
4. 验证邮箱后完成注册

### 2. 安装Git

#### Windows系统：
1. 访问 [Git官网](https://git-scm.com/downloads)
2. 下载Windows版本安装包
3. 运行安装程序，一路点击"Next"完成安装

#### 验证安装：
打开终端（命令提示符或PowerShell），输入：
```bash
git --version
```
如果显示Git版本号，说明安装成功。

### 3. 配置Git

首次使用Git需要配置用户信息：

```bash
git config --global user.name "你的GitHub用户名"
git config --global user.email "你的GitHub邮箱"
```

## 三、基本操作

### 1. 创建本地仓库

#### 方法一：从GitHub克隆仓库

```bash
# 克隆远程仓库到本地
git clone https://github.com/你的用户名/仓库名.git

# 进入项目目录
cd 仓库名
```

#### 方法二：本地初始化仓库

```bash
# 创建并进入项目目录
mkdir 项目名
cd 项目名

# 初始化Git仓库
git init
```

### 2. 文件操作

#### 查看文件状态

```bash
git status
```

- **红色**：未跟踪的文件（新文件）
- **绿色**：已暂存的文件（准备提交）

#### 添加文件到暂存区

```bash
# 添加单个文件
git add 文件名

# 添加所有文件
git add .

# 添加特定类型文件
git add *.java
```

#### 提交更改

```bash
git commit -m "提交信息"
```

提交信息要清晰描述你做了什么修改，例如：
- `git commit -m "添加用户登录功能"`
- `git commit -m "修复登录页面样式问题"`
- `git commit -m "更新README文档"`

### 3. 远程仓库操作

#### 添加远程仓库

```bash
git remote add origin https://github.com/你的用户名/仓库名.git
```

#### 查看远程仓库

```bash
git remote -v
```

#### 推送到远程仓库

```bash
# 首次推送（设置默认分支）
git push -u origin main

# 后续推送
git push
```

#### 从远程仓库拉取更新

```bash
git pull
```

## 四、分支管理

### 1. 创建分支

```bash
# 创建并切换到新分支
git checkout -b 分支名

# 或者分两步
git branch 分支名
git checkout 分支名
```

### 2. 查看分支

```bash
# 查看所有分支
git branch -a

# 查看当前分支
git branch
```

### 3. 切换分支

```bash
git checkout 分支名
```

### 4. 删除分支

```bash
# 删除本地分支
git branch -d 分支名

# 强制删除（未合并的分支）
git branch -D 分支名

# 删除远程分支
git push origin --delete 分支名
```

### 5. 合并分支

```bash
# 先切换到主分支
git checkout main

# 合并其他分支到主分支
git merge 分支名
```

## 五、常见问题解决

### 1. 忘记设置用户信息

```bash
git config --global user.name "你的用户名"
git config --global user.email "你的邮箱"
```

### 2. 推送失败

**问题**：`Permission denied`

**解决**：
- 检查GitHub账号密码是否正确
- 或者使用SSH密钥认证

### 3. 合并冲突

当多人修改同一文件的同一部分时会发生冲突。

**解决步骤**：
1. 打开冲突文件
2. 手动编辑解决冲突（删除冲突标记）
3. 添加解决后的文件
4. 提交更改

冲突标记示例：
```
<<<<<<< HEAD
这是当前分支的内容
=======
这是要合并的分支的内容
>>>>>>> 分支名
```

### 4. 撤销操作

```bash
# 撤销工作区的修改
git checkout -- 文件名

# 撤销暂存区的修改
git reset HEAD 文件名

# 撤销提交（谨慎使用）
git reset --hard 提交ID
```

## 六、日常工作流程

### 开发新功能

1. **创建分支**
   ```bash
   git checkout -b feature-新功能名
   ```

2. **编写代码**
   - 修改文件
   - 添加新文件

3. **提交更改**
   ```bash
   git add .
   git commit -m "添加新功能描述"
   ```

4. **推送分支**
   ```bash
   git push -u origin feature-新功能名
   ```

5. **创建Pull Request**
   - 在GitHub网页上点击"New pull request"
   - 选择你的分支合并到main分支

### 同步更新

```bash
# 切换到主分支
git checkout main

# 拉取最新代码
git pull

# 如果有新功能分支，合并到主分支
git merge feature-分支名
```

## 七、实用技巧

### 1. 查看提交历史

```bash
# 查看所有提交
git log

# 查看最近几次提交
git log -n 5

# 查看简洁的提交历史
git log --oneline
```

### 2. 查看文件修改

```bash
# 查看工作区修改
git diff

# 查看暂存区修改
git diff --cached

# 查看特定文件修改
git diff 文件名
```

### 3. 标签管理

```bash
# 创建标签
git tag v1.0.0

# 推送标签
git push origin v1.0.0

# 查看所有标签
git tag
```

### 4. .gitignore文件

创建`.gitignore`文件来忽略不需要提交的文件：

```gitignore
# 编译输出
target/
build/

# 日志文件
*.log
logs/

# IDE文件
.idea/
.vscode/
*.swp
*.swo

# 系统文件
.DS_Store
Thumbs.db
```

## 八、团队协作

### 1. Fork仓库

1. 在GitHub上点击"Fork"按钮复制别人的仓库
2. 克隆Fork的仓库到本地
3. 添加原仓库为上游仓库
   ```bash
   git remote add upstream https://github.com/原作者/仓库名.git
   ```

### 2. Pull Request流程

1. 创建新分支开发功能
2. 提交代码并推送到你的GitHub
3. 在GitHub上创建Pull Request
4. 等待原作者审核合并

### 3. 同步上游更新

```bash
# 获取上游仓库更新
git fetch upstream

# 合并到本地主分支
git checkout main
git merge upstream/main
```

## 九、图形化工具

如果你不喜欢使用终端，可以使用图形化工具：

### 推荐工具

- **GitHub Desktop**：官方桌面客户端，简单易用
- **SourceTree**：功能强大的Git客户端
- **VS Code**：内置Git支持

## 十、安全提示

1. **不要提交敏感信息**：密码、API密钥、个人信息等
2. **使用SSH密钥**：比密码更安全
3. **定期备份**：虽然GitHub有备份，但本地也要备份重要文件

## 十一、练习项目

可以按照以下步骤练习：

1. 在GitHub创建一个新仓库
2. 克隆到本地
3. 创建一个简单的项目（如Hello World）
4. 提交并推送
5. 创建新分支
6. 修改代码并合并
7. 删除分支

## 十二、常见命令速查表

| 命令 | 功能 |
|------|------|
| `git init` | 初始化仓库 |
| `git clone <url>` | 克隆仓库 |
| `git add <file>` | 添加文件到暂存区 |
| `git commit -m "message"` | 提交更改 |
| `git push` | 推送到远程仓库 |
| `git pull` | 拉取远程更新 |
| `git status` | 查看文件状态 |
| `git branch` | 查看分支 |
| `git checkout <branch>` | 切换分支 |
| `git merge <branch>` | 合并分支 |
| `git log` | 查看提交历史 |
| `git diff` | 查看文件差异 |

---

**记住**：Git的核心是"提交-推送-拉取"的循环。多练习几次，你就会越来越熟练！

如果遇到问题，可以：
1. 查看Git错误提示
2. 在Google搜索错误信息
3. 访问 [Git官方文档](https://git-scm.com/doc)
4. 在GitHub社区寻求帮助

祝你使用GitHub愉快！ 🚀