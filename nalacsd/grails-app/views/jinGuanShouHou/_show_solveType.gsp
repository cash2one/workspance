<script type="text/javascript">
$(function(){
	if ($(".origen").val() == "3"){
		$(".returnGoods").show(500)
		$(".resend").show(500)
		$(".remit").show(500)
	}else 
	if ($(".origen").val() == "2" || $(".origen").val() == "9"){
		$(".resend").show(500)
	}else
	if ($(".origen").val() == "4"){
		$(".returnGoods").show(500)
		$(".remit").show(500)
	}else
	if ($(".origen").val() == "5"){
		$(".returnGoods").show(500)
		$(".refund").show(500)
		$(".remit").show(500)
	}else
	if ($(".origen").val() == "6"){
		$(".remit").show(500)
	}else
	if ($(".origen").val() == "7"){
		$(".refund").show(500)
	}else
    if ($(".origen").val() == "11"){
        $(".coupon").show(500)
    }

	if ($(".questionStatus").val() == "18" || $(".questionStatus").val() == "19"){
		$(".questionReason").show(500)
	}else{		
		$(".questionReason").hide(500)
	}

    if ($(".moneyRecordType").val() == "returnWait"){
        $(".returnGoodsConfirm").show(500)
    }else{
        $(".returnGoodsConfirm").hide(500)
    }

	$(".origen").change(function(){
		if($(this).val() == "3"){
			$(".returnGoods").show(500)
			$(".resend").show(500)
			$(".refund").hide(500)
			$(".remit").show(500)
            $(".coupon").hide(500)
		}else
		if ($(this).val() == "2" || $(this).val() == "9"){
			$(".returnGoods").hide(500)
			$(".resend").show(500)
			$(".refund").hide(500)				
			$(".remit").hide(500)
            $(".coupon").hide(500)
		}else
		if ($(this).val() == "4"){
			$(".returnGoods").show(500)
			$(".resend").hide(500)
			$(".refund").hide(500)
			$(".remit").show(500)
            $(".coupon").hide(500)
		}else
		if ($(this).val() == "5"){
			$(".returnGoods").show(500)
			$(".resend").hide(500)
			$(".refund").show(500)
			$(".remit").show(500)
            $(".coupon").hide(500)
		}else
		if ($(this).val() == "6"){
			$(".returnGoods").hide(500)
			$(".resend").hide(500)
			$(".refund").hide(500)
			$(".remit").show(500)
            $(".coupon").hide(500)
		}else
		if ($(this).val() == "7"){
			$(".returnGoods").hide(500)
			$(".resend").hide(500)
			$(".refund").show(500)
			$(".remit").hide(500)
            $(".coupon").hide(500)
		}else
        if ($(".origen").val() == "11"){
            $(".returnGoods").hide(500)
            $(".resend").hide(500)
            $(".refund").hide(500)
            $(".remit").hide(500)
            $(".coupon").show(500)
        }else{
			$(".returnGoods").hide(500)
			$(".resend").hide(500)
			$(".refund").hide(500)
			$(".remit").hide(500)
            $(".coupon").hide(500)
		}
	});

	$(".questionStatus").change(function(){
		if($(this).val() == "18" || $(this).val() == "19"){
			$(".questionReason").show(500)
		}else{
			$(".questionReason").hide(500)
		}
	});

    $(".moneyRecordType").change(function(){
        if($(this).val() == "returnWait"){
            $(".returnGoodsConfirm").show(500)
        }else{
            $(".returnGoodsConfirm").hide(500)
        }
    });

})
</script>
