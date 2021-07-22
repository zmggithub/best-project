<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="jquery.min.js"></script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>index</title>
</head>
<body>
<div>
    <h1 style="text-align:center;margin-top:200px;">项目日报表</h1>
    <dev>
        <dev class="btn" style="display:block;margin:0 auto;text-align:center;"><button>生成日报表</button></dev>
    </dev>
</div>

<div class="formPanel" id="createDialog" style="display:none;">
    <form id="saveCusmanagerAjax" action="" method="post">
        <div class="formPanelBody">
            <table class="formTable" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="tdLabel">
                        <span class="required"></span>省份编号:
                    </td>
                    <td  >
                        <input value="" id="provinceNo" name="provinceNo"    />
                    </td>
                    <td class="tdLabel">
                        <span class="required"></span>省份名称:
                    </td>
                    <td  >
                        <input value=" " id="province" name="province"    />
                    </td>
                </tr>
                <tr>
                    <td class="tdLabel">
                        <span class="required"></span>市区编号:
                    </td>
                    <td  >
                        <input value=" " id="cityNo" name="cityNo"    />
                    </td>
                    <td class="tdLabel">
                        <span class="required"></span>市区名称:
                    </td>
                    <td  >
                        <input value=" " id="city" name="city"    />
                    </td>
                </tr>
                <tr>
                    <td class="tdLabel">
                        <span class="required"></span>部门编号:
                    </td>
                    <td  >
                        <input value=" " id="deptNo" name="deptNo"    />
                    </td>
                    <td class="tdLabel">
                        <span class="required"></span>部门名称:
                    </td>
                    <td  >
                        <input value=" " id="dept" name="dept"    />
                    </td>
                </tr>
                <tr>
                    <td class="tdLabel">
                        <span class="required"></span>团队编号:
                    </td>
                    <td  >
                        <input value=" " id="teamNo" name="teamNo"    />
                    </td>
                    <td class="tdLabel">
                        <span class="required"></span>团队名称:
                    </td>
                    <td  >
                        <input value=" " id="team" name="team"    />
                    </td>
                </tr>
                <tr>
                    <td class="tdLabel">
                        <span class="required"></span>经理编号:
                    </td>
                    <td  >
                        <input value=" " id="managerNo" name="managerNo"  />
                    </td>
                    <td class="tdLabel">
                        <span class="required"></span>经理姓名:
                    </td>
                    <td  >
                        <input value=" " id="manager" name="manager"    />
                    </td>
                </tr>
                <tr>
                    <td class="tdLabel">
                        <span class="required"></span>是否启用:
                    </td>
                    <td colspan="3" >
                        <input value=" " id="isused" name="isused"    />
                    </td>
                </tr>
            </table>
        </div>
        <div class="formPanelFoot">
            <table cellpadding="0" cellspacing="0">
                <tr>
                    <td>
                        <input id="submitButton" οnclick="saveCusmanagerByAjax();" type="button" value="提交" />
                        <input type="button" value="后退" οnclick="history.back();" />
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
</body>
</html>

<script>
    $('.btn').click(function(){


            $('#createDialog').dialog({
                    title : '新增客户经理'
                },{
                    height : 580
                },{
                    width : 780
                },{
                    resizable : true
                },{
                    modal : true
                },{
                    show : 'clip'
                }

    });
</script>