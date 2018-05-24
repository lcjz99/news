<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <style>
        .lifangti {
            transform: rotateX(-33.5deg) rotateY(45deg);
            transform-origin: 50px 0px;
            transform-style: preserve-3d;
            -webkit-transform: rotateX(-33.5deg) rotateY(45deg);
            -webkit-transform-origin: 50px 0px;
            -webkit-transform-style: preserve-3d;
            -moz-transform: rotateX(-33.5deg) rotateY(45deg);
            -moz-transform-origin: 50px 0px;
            top: 50%;
            left: 50%;
            margin: -100px 0 0 -50px;
            position: absolute;
            display: block;
            animation: xuanzhuan 5s infinite ease;
            -moz-animation: xuanzhuan 5s infinite ease; /* Firefox */
            -webkit-animation: xuanzhuan 5s infinite ease; /* Safari �� Chrome */
            -o-animation: xuanzhuan 5s infinite ease; /* Opera */
        }

        .lifangti > .bgabox {
            width: 100px;
            height: 100px;
            position: absolute;
            opacity: 0.6;
        }

        .leftm {
            background-color: #ff6a00;
            transform: rotateY(90deg) translateZ(-50PX);
            -moz-transform: rotateY(90deg) translateZ(-50PX);
            -webkit-transform: rotateY(90deg) translateZ(-50PX);
        }

        .rightm {
            background-color: #6faed9;
            transform: rotateY(90deg) translateZ(50px);
            -moz-transform: rotateY(90deg) translateZ(50px);
            -webkit-transform: rotateY(90deg) translateZ(50px);
        }

        .topm {
            transform: rotateX(90deg) translateZ(50px);
            -moz-transform: rotateX(90deg) translateZ(50px);
            -webkit-transform: rotateX(90deg) translateZ(50px);
            background-color: #ff0000;
            opacity: 1;
        }

        .bottomm {
            transform: rotateX(90deg) translateZ(-50px);
            -moz-transform: rotateX(90deg) translateZ(-50px);
            -webkit-transform: rotateX(90deg) translateZ(-50px);
            background-color: #52d538;
        }

        .beform {
            background-color: #b12d9e;
            transform: translateZ(50px);
            -moz-transform: translateZ(50px);
            -webkit-transform: translateZ(50px);
        }

        .afterm {
            background-color: #138fc6;
            transform: translateZ(-50px);
            -moz-transform: translateZ(-50px);
            -webkit-transform: translateZ(-50px);
        }

        @keyframes xuanzhuan {
            from {
                transform: rotateX(-33.5deg) rotateY(45deg);
                -webkit-transform: rotateX(-33.5deg) rotateY(45deg);
                -moz-transform: rotateX(-33.5deg) rotateY(45deg);
            }

            to {
                transform: rotateX(-33.5deg) rotateY(765deg);
                -webkit-transform: rotateX(-33.5deg) rotateY(765deg);
                -moz-transform: rotateX(-33.5deg) rotateY(765deg);
            }
        }

        .lifangtimin {
            transform-origin: 25px 0px;
            transform-style: preserve-3d;
            -webkit-transform-origin: 25px 0px;
            top: 50%;
            margin: -50px 0 0 -25px;
            position: absolute;
            display: block;
            transform: translateX(50px) translateY(75px) rotateY(0deg);
            -webkit-transform: translateX(50px) translateY(75px) rotateY(0deg);
            -moz-transform: translateX(50px) translateY(75px) rotateY(0deg);
            animation: xuanzhuanm 5s infinite ease;
            -moz-animation: xuanzhuanm 5s infinite ease; /* Firefox */
            -webkit-animation: xuanzhuanm 5s infinite ease; /* Safari �� Chrome */
            -o-animation: xuanzhuanm 5s infinite ease; /* Opera */
        }

        .lifangtimin > div {
            width: 50px;
            height: 50px;
            position: absolute;
            opacity: 1;
        }

        .leftmm {
            background-color: #ff6a00;
            transform: rotateY(90deg) translateZ(-25px);
            -webkit-transform: rotateY(90deg) translateZ(-25px);
            -moz-transform: rotateY(90deg) translateZ(-25px);
        }

        .rightmm {
            background-color: #6faed9;
            transform: rotateY(90deg) translateZ(25px);
            -webkit-transform: rotateY(90deg) translateZ(25px);
            -moz-transform: rotateY(90deg) translateZ(25px);
        }

        .topmm {
            transform: rotateX(90deg) translateZ(25px);
            -webkit-transform: rotateX(90deg) translateZ(25px);
            -moz-transform: rotateX(90deg) translateZ(25px);
            background-color: #ff0000;
            opacity: 1;
        }

        .bottommm {
            transform: rotateX(90deg) translateZ(-25px);
            -webkit-transform: rotateX(90deg) translateZ(-25px);
            -moz-transform: rotateX(90deg) translateZ(-25px);
            background-color: #52d538;
        }

        .beformm {
            background-color: #b12d9e;
            transform: translateZ(25px);
            -webkit-transform: translateZ(25px);
            -moz-transform: translateZ(25px);
        }

        .aftermm {
            background-color: #138fc6;
            transform: translateZ(-25px);
            -webkit-transform: translateZ(-25px);
            -moz-transform: translateZ(-25px);
        }

        @keyframes xuanzhuanm {
            from {
                transform: translateX(50px) translateY(75px) rotateY(0deg);
                -webkit-transform: translateX(50px) translateY(75px) rotateY(0deg);
                -moz-transform: translateX(50px) translateY(75px) rotateY(0deg);
            }

            to {
                transform: translateX(50px) translateY(75px) rotateY(-1080deg);
                -webkit-transform: translateX(50px) translateY(75px) rotateY(-1080deg);
                -moz-transform: translateX(50px) translateY(75px) rotateY(-1080deg);
            }
        }
    </style>
</head>
<body>

<div class="lifangti">

    <div class="beform bgabox"></div>
    <div class="afterm bgabox"></div>
    <div class="leftm bgabox"></div>
    <div class="rightm bgabox"></div>
    <div class="topm bgabox"></div>
    <div class="bottomm bgabox"></div>

    <div class="lifangtimin">
        <div class="beformm"></div>
        <div class="aftermm"></div>
        <div class="leftmm"></div>
        <div class="rightmm"></div>
        <div class="topmm"></div>
        <div class="bottommm"></div>
    </div>

</div>

</body>
</html>