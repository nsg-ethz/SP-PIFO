/* -*- P4_14 -*- */

#ifdef __TARGET_TOFINO__
#include <tofino/constants.p4>
#include <tofino/intrinsic_metadata.p4>
#include <tofino/primitives.p4>
#include <tofino/stateful_alu_blackbox.p4>
#else
#error This program is intended to compile for Tofino P4 architecture only
#endif

/*************************************************************************
 ***********************  H E A D E R S  *********************************
 *************************************************************************/
 
header_type ethernet_t {
    fields {
        dstAddr   : 48;
        srcAddr   : 48;
        etherType : 16;
    }
}

header_type vlan_tag_t {
    fields {
        pcp       : 3;
        cfi       : 1;
        vid       : 12;
        etherType : 16;
    }
}

header_type ipv4_t {
    fields {
        version        : 4;
        ihl            : 4;
        diffserv       : 8;
        totalLen       : 16;
        identification : 16;
        flags          : 3;
        fragOffset     : 13;
        ttl            : 8;
        protocol       : 8;
        hdrChecksum    : 16;
        srcAddr        : 32;
        dstAddr        : 32;
    }
}

/*************************************************************************
 ***********************  M E T A D A T A  *******************************
 *************************************************************************/

 header_type pifo_metadata_t {
    fields {
        register_result0  : 32;  
        register_result1  : 32;
        register_result2  : 32;  
        register_result3  : 32;
        register_result4  : 32;  
        register_result5  : 32;
        register_result6  : 32;   
        rank              : 32;       
        WIN               : 32; 
        qid               : 5;
    }
}
metadata pifo_metadata_t pifo_metadata;

/*************************************************************************
 ***********************  P A R S E R  ***********************************
 *************************************************************************/

header ethernet_t ethernet;
header vlan_tag_t vlan_tag[2];
header ipv4_t     ipv4;

parser start {
    extract(ethernet);
    return select(ethernet.etherType) {
        0x8100 : parse_vlan_tag;
        0x0800 : parse_ipv4;
        default: ingress;
    }
}

parser parse_vlan_tag {
    extract(vlan_tag[next]);
    return select(latest.etherType) {
        0x8100 : parse_vlan_tag;
        0x0800 : parse_ipv4;
        default: ingress;
    }
}

parser parse_ipv4 {
    extract(ipv4);
    set_metadata(pifo_metadata.rank, 0);
    set_metadata(pifo_metadata.register_result0, 0);
    set_metadata(pifo_metadata.register_result1, 0);
    set_metadata(pifo_metadata.register_result2, 0);
    set_metadata(pifo_metadata.register_result3, 0);
    set_metadata(pifo_metadata.register_result4, 0);
    set_metadata(pifo_metadata.register_result5, 0);
    set_metadata(pifo_metadata.register_result6, 0);
    return ingress;
}

/*************************************************************************
 **************  I N G R E S S   P R O C E S S I N G   *******************
 *************************************************************************/

register queue_level0 {
    width : 32;
    instance_count : 65536;
}

register queue_level1 {
    width : 32;
    instance_count : 65536;
}

register queue_level2 {
    width : 32;
    instance_count : 65536;
}

register queue_level3 {
    width : 32;
    instance_count : 65536;
}

register queue_level4 {
    width : 32;
    instance_count : 65536;
}

register queue_level5 {
    width : 32;
    instance_count : 65536;
}

register queue_level6 {
    width : 32;
    instance_count : 65536;
}

register queue_level7 {
    width : 32;
    instance_count : 65536;
}

action send(port) {
    modify_field(ig_intr_md_for_tm.ucast_egress_port, port);
    modify_field(ipv4.diffserv, 0);
}

action l3_switch(new_mac_da, new_mac_sa, port){
    modify_field(ethernet.dstAddr, new_mac_da);
    modify_field(ethernet.srcAddr, new_mac_sa);
    add_to_field(ipv4.ttl, -1);
    modify_field(ig_intr_md_for_tm.ucast_egress_port, port);
}

action discard() {
    modify_field(ig_intr_md_for_tm.drop_ctl, 1);
}

blackbox stateful_alu read_data0 {
    reg: queue_level0;

    condition_lo: pifo_metadata.rank >= register_lo;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: pifo_metadata.rank;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 0;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result0;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data1 {
    reg: queue_level1;

    condition_lo: pifo_metadata.rank >= register_lo;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: pifo_metadata.rank;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 0;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result1;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data2 {
    reg: queue_level2;

    condition_lo: pifo_metadata.rank >= register_lo;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: pifo_metadata.rank;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 0;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result2;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data3 {
    reg: queue_level3;

    condition_lo: pifo_metadata.rank >= register_lo;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: pifo_metadata.rank;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 0;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result3;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data4 {
    reg: queue_level4;

    condition_lo: pifo_metadata.rank >= register_lo;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: pifo_metadata.rank;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 0;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result4;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data5 {
    reg: queue_level5;

    condition_lo: pifo_metadata.rank >= register_lo;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: pifo_metadata.rank;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 0;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result5;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data6 {
    reg: queue_level6;

    condition_lo: pifo_metadata.rank >= register_lo;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: pifo_metadata.rank;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 0;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result6;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data7 {
    reg: queue_level7;

    condition_lo: pifo_metadata.rank >= register_lo;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: pifo_metadata.rank;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: pifo_metadata.rank;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 0;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: register_lo - pifo_metadata.rank; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.WIN;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data0_WIN {
    reg: queue_level0;

    condition_lo: 1;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: register_lo - pifo_metadata.WIN;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo - pifo_metadata.WIN;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 1;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result0;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data1_WIN {
    reg: queue_level1;

    condition_lo: 1;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: register_lo - pifo_metadata.WIN;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo - pifo_metadata.WIN;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 1;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result1;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data2_WIN {
    reg: queue_level2;

    condition_lo: 1;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: register_lo - pifo_metadata.WIN;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo - pifo_metadata.WIN;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 1;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result2;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data3_WIN {
    reg: queue_level3;

    condition_lo: 1;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: register_lo - pifo_metadata.WIN;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo - pifo_metadata.WIN;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 1;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1;  

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result3;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data4_WIN {
    reg: queue_level4;

    condition_lo: 1;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: register_lo - pifo_metadata.WIN;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo - pifo_metadata.WIN;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 1;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result4;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data5_WIN {
    reg: queue_level5;

    condition_lo: 1;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: register_lo - pifo_metadata.WIN;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo - pifo_metadata.WIN;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 1;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    output_value          : alu_hi;
    output_dst            : pifo_metadata.register_result5;

    initial_register_lo_value : 0;
}

blackbox stateful_alu read_data6_WIN {
    reg: queue_level6;

    condition_lo: pifo_metadata.rank >= register_lo;

    condition_lo: 1;

    update_lo_1_predicate: condition_lo; 
    update_lo_1_value: register_lo - pifo_metadata.WIN;
    update_lo_2_predicate: not condition_lo;
    update_lo_2_value: register_lo - pifo_metadata.WIN;

    update_hi_1_predicate: condition_lo;
    update_hi_1_value: 1;
    update_hi_2_predicate: not condition_lo;
    update_hi_2_value: 1; 

    initial_register_lo_value : 0;
}

action update_rank() {
    modify_field(pifo_metadata.rank, ipv4.srcAddr);
}

action scan_queue0() {
    read_data0.execute_stateful_alu(0);
}

action scan_queue1() {
    read_data1.execute_stateful_alu(0);
}

action scan_queue2() {
    read_data2.execute_stateful_alu(0);
}

action scan_queue3() {
    read_data3.execute_stateful_alu(0);
}

action scan_queue4() {
    read_data4.execute_stateful_alu(0);
}

action scan_queue5() {
    read_data5.execute_stateful_alu(0);
}

action scan_queue6() {
    read_data6.execute_stateful_alu(0);
}

action scan_queue7() {
    read_data7.execute_stateful_alu(0);
}

action enqueue_to_zero() {
    modify_field(pifo_metadata.qid, 0);
}

action enqueue_to_one() {
    modify_field(pifo_metadata.qid, 1);
}

action enqueue_to_two() {
    modify_field(pifo_metadata.qid, 2);
}

action enqueue_to_three() {
    modify_field(pifo_metadata.qid, 3);
}

action enqueue_to_four() {
    modify_field(pifo_metadata.qid, 4);
}

action enqueue_to_five() {
    modify_field(pifo_metadata.qid, 5);
}

action enqueue_to_six() {
    modify_field(pifo_metadata.qid, 6);
}

action enqueue_to_seven() {
    modify_field(pifo_metadata.qid, 7); 
}

action enqueue_resubmitted() {
    modify_field(ig_intr_md_for_tm.qid, pifo_metadata.qid); 
}

action scan_queue0_WIN() {
    read_data0_WIN.execute_stateful_alu(0);
}

action scan_queue1_WIN() {
    read_data1_WIN.execute_stateful_alu(0);
}

action scan_queue2_WIN() {
    read_data2_WIN.execute_stateful_alu(0);
}

action scan_queue3_WIN() {
    read_data3_WIN.execute_stateful_alu(0);
}

action scan_queue4_WIN() {
    read_data4_WIN.execute_stateful_alu(0);
}

action scan_queue5_WIN() {
    read_data5_WIN.execute_stateful_alu(0);
}

action scan_queue6_WIN() {
    read_data6_WIN.execute_stateful_alu(0);
}

table ipv4_host {
    reads {
        ipv4.dstAddr : exact;
    }
    actions {
        send;
        l3_switch;
        discard;
    }
    size : 15;
}

table ipv4_lpm {
    reads {
        ipv4.dstAddr : lpm;
    }
    actions {
        send;
        l3_switch;
        discard;
    }
    size : 15;
}

table do_update_rank {
    actions {
        update_rank;
    }
    default_action: update_rank(); 
    size: 0;
}

table do_scan_queue0 {
    actions {
        scan_queue0;
    }
    default_action: scan_queue0(); 
    size: 0;
}

table do_scan_queue1 {
    actions {
        scan_queue1;
    }
    default_action: scan_queue1(); 
    size: 0;
}

table do_scan_queue2 {
    actions {
        scan_queue2;
    }
    default_action: scan_queue2(); 
    size: 0;
}

table do_scan_queue3 {
    actions {
        scan_queue3;
    }
    default_action: scan_queue3(); 
    size: 0;
}

table do_scan_queue4 {
    actions {
        scan_queue4;
    }
    default_action: scan_queue4(); 
    size: 0;
}

table do_scan_queue5 {
    actions {
        scan_queue5;
    }
    default_action: scan_queue5(); 
    size: 0;
}

table do_scan_queue6 {
    actions {
        scan_queue6;
    }
    default_action: scan_queue6(); 
    size: 0;
}

table do_scan_queue7 {
    actions {
        scan_queue7;
    }
    default_action: scan_queue7(); 
    size: 0;
}

table do_enqueue_to_zero {
    actions {
        enqueue_to_zero;
    }
    default_action: enqueue_to_zero(); 
    size: 0;
}

table do_enqueue_to_one {
    actions {
        enqueue_to_one;
    }
    default_action: enqueue_to_one(); 
    size: 0;
}

table do_enqueue_to_two {
    actions {
        enqueue_to_two;
    }
    default_action: enqueue_to_two(); 
    size: 0;
}

table do_enqueue_to_three {
    actions {
        enqueue_to_three;
    }
    default_action: enqueue_to_three(); 
    size: 0;
}

table do_enqueue_to_four {
    actions {
        enqueue_to_four;
    }
    default_action: enqueue_to_four(); 
    size: 0;    
}

table do_enqueue_to_five {
    actions {
        enqueue_to_five;
    }
    default_action: enqueue_to_five(); 
    size: 0;
}

table do_enqueue_to_six {
    actions {
        enqueue_to_six;
    }
    default_action: enqueue_to_six(); 
    size: 0;
}

table do_enqueue_to_seven {
    actions {
        enqueue_to_seven;
    }
    default_action: enqueue_to_seven(); 
    size: 0;
}

table do_enqueue_resubmitted {
    actions {
        enqueue_resubmitted;
    }
    default_action: enqueue_resubmitted(); 
    size: 0;
}

table do_scan_queue0_WIN {
    actions {
        scan_queue0_WIN;
    }
    default_action: scan_queue0_WIN(); 
    size: 0;
}

table do_scan_queue1_WIN {
    actions {
        scan_queue1_WIN;
    }
    default_action: scan_queue1_WIN(); 
    size: 0;
}

table do_scan_queue2_WIN {
    actions {
        scan_queue2_WIN;
    }
    default_action: scan_queue2_WIN(); 
    size: 0;
}

table do_scan_queue3_WIN {
    actions {
        scan_queue3_WIN;
    }
    default_action: scan_queue3_WIN(); 
    size: 0;
}

table do_scan_queue4_WIN {
    actions {
        scan_queue4_WIN;
    }
    default_action: scan_queue4_WIN(); 
    size: 0;
}

table do_scan_queue5_WIN {
    actions {
        scan_queue5_WIN;
    }
    default_action: scan_queue5_WIN(); 
    size: 0;
}

table do_scan_queue6_WIN {
    actions {
        scan_queue6_WIN;
    }
    default_action: scan_queue6_WIN(); 
    size: 0;
}

field_list resubmit_list {
    pifo_metadata.WIN;
    pifo_metadata.qid;
}

action packet_resubmit(){
    resubmit(resubmit_list);
}

table do_packet_resubmit {
    actions {
        packet_resubmit;
    }
}

control ingress {
    if (valid(ipv4)) {
        if ((ipv4.ttl & 0xfe) != 0){ 
            apply(do_update_rank);
            if(ig_intr_md.resubmit_flag == 0){
                apply(do_scan_queue0);               
                if(pifo_metadata.register_result0 == 0){
                    apply(do_enqueue_to_zero);
                } else {
                    apply(do_scan_queue1);               
                    if(pifo_metadata.register_result1 == 0){
                        apply(do_enqueue_to_one);
                    } else {
                        apply(do_scan_queue2);               
                        if(pifo_metadata.register_result2 == 0){
                            apply(do_enqueue_to_two);
                        } else {
                            apply(do_scan_queue3);               
                            if(pifo_metadata.register_result3 == 0){
                                apply(do_enqueue_to_three);
                            }
                            else {
                                apply(do_scan_queue4);               
                                if(pifo_metadata.register_result4 == 0){
                                    apply(do_enqueue_to_four);
                                }
                                else {
                                    apply(do_scan_queue5);               
                                    if(pifo_metadata.register_result5 == 0){
                                        apply(do_enqueue_to_five);
                                    } else {
                                        apply(do_scan_queue6);               
                                        if(pifo_metadata.register_result6 == 0){
                                            apply(do_enqueue_to_six);
                                        } else {
                                            apply(do_scan_queue7);   
                                            apply(do_enqueue_to_seven);
                                            if(pifo_metadata.WIN != 0){
                                                apply(do_packet_resubmit);
                                            }                                            
                                        }
                                    }
                                }
                            }
                        }
                    }
                } 
            } else {
                apply(do_scan_queue0_WIN);             
                if(pifo_metadata.register_result0 == 0){
                    //
                } else {
                    apply(do_scan_queue1_WIN);               
                    if(pifo_metadata.register_result1 == 0){
                        //
                    } else {
                        apply(do_scan_queue2_WIN);               
                        if(pifo_metadata.register_result2 == 0){
                            //
                        } else {
                            apply(do_scan_queue3_WIN);               
                            if(pifo_metadata.register_result3 == 0){
                                //
                            }
                            else {
                                apply(do_scan_queue4_WIN);               
                                if(pifo_metadata.register_result4 == 0){
                                    //
                                }
                                else {
                                    apply(do_scan_queue5_WIN);               
                                    if(pifo_metadata.register_result5 == 0){
                                        //
                                    } else {
                                        apply(do_scan_queue6_WIN);               
                                    }
                                }
                            }
                        }
                    }
                }
            }    
            apply(do_enqueue_resubmitted);   
            apply(ipv4_host) {
                miss {
                    apply(ipv4_lpm);
                }
            }
        }
    }
}

/*************************************************************************
 ****************  E G R E S S   P R O C E S S I N G   *******************
 *************************************************************************/

control egress {
}






