# vscode에서 typescript 프로젝트를 준비하는 방법

## 목록
* [typescript 프로젝트 준비](#typescript-프로젝트-준비)
* [lint 설정](#lint-설정)
* [webpack 설정](#webpack-설정)
* [debug 설정](#debug-설정)

### typescript 프로젝트 준비
> [vscode - typescript 설명](https://code.visualstudio.com/docs/languages/typescript)  

> node js는 이미 설치했다고 가정 / 정식 튜토리얼: [TypeScript tutorial in Visual Studio Code](https://code.visualstudio.com/docs/typescript/typescript-tutorial)  

1. 프로젝트 폴더 생성
2. `npm init` 실행
3. `npm install typescript --save-dev`로 타입스크립트 모듈 설치
4. `npx tsc --init`로 `tsconfig.json` 파일 생성
5. 생성된 `tsconfig.json`에서 빌드 설정 조정
6. `src` 폴더 생성후, 아래에 `main.ts` 생성

### lint 설정
1. `npm install tslint --save-dev`로 tslint 모듈 설치
2. `tslint.json` 파일 생성
```json
// default boilerplate - tslint.json
{
    "defaultSeverity": "error",
    "extends": "",
    "jsRules": {},
    "rules": {
        "eofline": false
    },
    "rulesDirectory": []
}
```
3. (Optional) airbnb 사용시
    1. `npm i tslint-config-airbnb` 로 airbnb 모듈 설치
    2. `tslint.json` 파일의 `extends`에 `tslint-config-airbnb` 추가

### webpack 설정
1. `npm install webpack-cli --save-dev`으로 webpack cli 설치
2. `npm install webpack --save-dev`으로 webpack 설치
3. `npm install ts-loader --save-dev`으로 typescript loader 설치
4. `webpack.config.js` 파일 생성
5. typescript 관련 설정 (참고 - [https://code.visualstudio.com/api/working-with-extensions/bundling-extension](https://code.visualstudio.com/api/working-with-extensions/bundling-extension))
```javascript
// webpack.config.js boilerplate
//@ts-check

'use strict';

const path = require('path');

const config = {
    target: 'node', // vscode extensions run in a Node.js-context 📖 -> https://webpack.js.org/configuration/node/

    entry: './src/main.ts', // the entry point of this extension, 📖 -> https://webpack.js.org/configuration/entry-context/
    output: {
        // the bundle is stored in the 'dist' folder (check package.json), 📖 -> https://webpack.js.org/configuration/output/
        path: path.resolve(__dirname, 'dist'),
        filename: 'main.js',
        devtoolModuleFilenameTemplate: '../[resource-path]'
    },
    devtool: 'source-map',
    externals: {
        vscode: 'commonjs vscode' // the vscode-module is created on-the-fly and must be excluded. Add other modules that cannot be webpack'ed, 📖 -> https://webpack.js.org/configuration/externals/
    },
    resolve: {
        // support reading TypeScript and JavaScript files, 📖 -> https://github.com/TypeStrong/ts-loader
        extensions: ['.ts', '.js']
    },
    module: {
        rules: [
            {
                test: /\.ts$/,
                exclude: /node_modules/,
                use: [
                    {
                        loader: 'ts-loader'
                    }
                ]
            }
        ]
    }
};
module.exports = config;
```

### debug 설정
1. `.vscode` 폴더에 디버그 설정파일인 `launch.json` 파일 생성
2. `.vscode` 폴더에 작업 설정파일인 `tasks.json` 파일 생성
3. `tasks.json`에 task 설정
```json
{
    "version": "2.0.0",
    "tasks": [
        // typescript build task 코드
        {
            "type": "typescript",
            "tsconfig": "javascript/typescript/boilerplate/create/tsconfig.json",
            "problemMatcher": [
                "$tsc"
            ],
            "presentation": {
                "echo": true,
                "reveal": "always",
                "focus": false,
                "panel": "shared",
                "showReuseMessage": true,
                "clear": false
            },
            "group": {
                "kind": "build",
                "isDefault": true
            },
            "label": "typescript build"
        },
        // webpack build task 코드
        {
            "type": "npm",
            "script": "webpack",
            "path": "javascript/typescript/boilerplate/create/",
            "problemMatcher": [
                "$tsc"
            ],
            "label": "npm: webpack build",
            "detail": "webpack --mode development"
        }
    ]
}
```
4. `launch.json`에 `chrome` 디버그 설정
```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "name": "Launch", // 디버그 이름 설정
            "request": "launch", // 디버그 request 설정
            "type": "pwa-chrome", // type 설정
            "preLaunchTask": "npm: webpack build", // 디버그 실행시, 먼저 실행할 task 설정 - task 파일의 label 참조가능
            "url": "${workspaceFolder}/javascript/typescript/boilerplate/create/html/index.html", // 어떤 html 파일에 연결할 것인지 설정
            "webRoot": "${workspaceFolder}"
        },
    ]
}
```