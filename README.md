

 <h5 >君も<a link=" http://webpage.yamanoko.fun"> 山の子鯖 </a>にいこう!</h5>
 <br>

<h1> UserFlower </h1>


MCサーバにWorldGuardを導入すると、サーバの建物を保護できる。
山の子鯖ではユーザひとりひとりにWorldGuardの使用を許可し、自由に土地を保護できるようにした。

しかしこの仕組みは保護された領域や建築物が残り続け、後続の新規参入者たちがワールドを十分に開拓できないという問題がある。

このプラグインは
「参加しなくなったユーザの建造物、保護領域を自動的に削除する」ことで、
新規参入者が遊ぶ領域を確保することを目的としている。


<hr>
<h2>導入方法</h2>
<br>

⚠︎ Spigot/PaperMCのサーバを想定しています

<br>

前提プラグイン 
* WorldGuard
* CoreProtect


<br>
jarディレクトリにある ファイル2点を サーバのpluginsフォルダに入れてください

* UserFlower-[バージョン名].jar
* UserFlower_LastLog.jar

<br>
<hr>

<h2>コマンド</h2>

><h3><b>/ufclean [border] [delAmount]</b></h3>

非アクティブユーザを取得し、ユーザのリージョンを削除、リージョンを消去範囲としてブロックを部分的に消していきます。コマンドを繰り返すと、最終的に消去範囲のブロックが全て消えていきます。

+ <h4>border : </h4>
    <p>border で指定した時間以上ログインしていないプレイヤーを非アクティブとみなします。</p>
    <p>ミリ秒のタイムスタンプで表現するため、1000 が 1秒に相当します。</p>
    <p>仮に1時間以上ログインしていないユーザを取得するには </p>
    <p>60 x 60 x 1000 = 3600000</p>
    <p>で指定するひつようがあります。</p>

<br>

+ <h4>delAmount :</h4>
    <p>一度の実行で消すブロックの量です。</p>
    <p>消されたリージョン範囲かつ、過去に置かれたブロックだけを消します。</p>
    <p>消去中の範囲には新しいリージョンを設定することができ、リージョンを設定してしまえば過去におかれたブロックも消されなくなります。</p>

    <br>

    <h3>使用例</h3>


    ><p>/ufclean 3600000 5</p>
    1時間以上ログインしていないプレイヤーのリージョン削除、一回の実行で消去範囲のブロックを5つ削除


        permission: "userflower.clean"

    <br><br>
><h3><b>/ufauto [enable] [border] [delAmount] [interval]</b></h3>

/ufcleanコマンドを定期的に実行します。
+ <h4>enable</h4>
    <p>trueまたはfalseを設定します</p>
    <p>trueだと定期的に自動実行されます</p>

+ <h4>interval</h4>
    <p>intervalで指定したティックごとに実行します。<p>
    <p>20ティックが1秒に相当するため、一時間ごとに実行したい時は</p>
    <p> 60 x 60 x 20 = 72000</p>
    <p>を指定する必要があります。</p>

    permission: "userflower.auto"








