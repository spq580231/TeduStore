package cn.tedu.store.test.text;

class ClassAndObjectLnitialize {

    public static void main(String[] args) {
        System.out.println("����Ĵ�ӡ���");
    }

    public ClassAndObjectLnitialize() {

        System.out.println("���췽��");
        System.out.println("�����ܺ����ҵ�����=" + ZhiShang + ",����=" + QingShang);
    }

    {
        System.out.println("��ͨ�����");
    }

    int ZhiShang = 250;
    static int QingShang = 666;

    static {
        System.out.println("��̬�����");
    }

}

