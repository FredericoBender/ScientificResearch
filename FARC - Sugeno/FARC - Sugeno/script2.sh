for a in {satimage,page-blocks};do
    for b in {5,50};do
        for c in {1,2,3,4,5};
            do java -cp build/classes FARCHD.Main param.txt $a $b $c 5;           
        done;
    done;
done;