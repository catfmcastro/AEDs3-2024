// int tam = padrao.length() - 1;
        // this.bom[tam] = 1;
        // int posicao = padrao.length();

        // for (int i = tam; i > 0; i--) {
        //     boolean var = false;
        //     int j = i - 1;

        //     for (; j >= 0; j--) {

        //         if (padrao.charAt(i) == padrao.charAt(j)) {
        //             posicao = j;

        //             if (j > 0 && padrao.charAt(i - 1) != padrao.charAt(j - 1)) {
        //                 int temp = j + 1;
        //                 var = true;
        //                 for (int k = i + 1; k < tam; k++, temp++) {
        //                     if (padrao.charAt(k) != padrao.charAt(temp)) {
        //                         var = false;
        //                         break;
        //                     }
        //                 }

        //                 if (var) {
        //                     bom[i - 1] = i - posicao;
        //                     break;
        //                 }

        //             }

        //         }

        //     }

        //     if(!var){
        //         for (int k = i; k < tam; k++) {
        //             if (padrao.charAt(k) == padrao.charAt(0)) {
        //                 posicao = k;
        //                 var = true;
        //                 int temp = 1;
        //                 for (int k2 = k + 1; k2 <= tam; k2++, temp++) {
        //                     if (padrao.charAt(k2) != padrao.charAt(temp)) {
        //                         var = false;
        //                         break;
        //                     }
        //                 }

        //                 if (var) {
        //                     bom[i] = posicao;
        //                 }
        //             }
        //         }
                
        //     }

        //     if (!var) {
        //         bom[i - 1] = tam + 1;
        //     }