folder = 'C:\Users\Anna\Documents\GitHub\AI\AI3\solutions\';
name = 'solution-1-4.txt';
fullname = [folder name];
data = importdata(fullname);
[rows,m] = size(data);
disp(data{1,1});
line = strsplit(data{1,1},{', ','[',']'});
cols = length(line);
water = zeros(rows,cols);
mountain = zeros(rows,cols);
forest = zeros(rows,cols);
grassland = zeros(rows,cols);
road = zeros(rows,cols);
obstical = zeros(rows,cols);
trace = zeros(rows,cols);
start = zeros(rows,cols);
final = zeros(rows,cols);
for i = 1:rows
    line = strsplit(data{i,1},{', ','[',']'});
    for j = 2:length(line)-1
        if(str2double(line{j}) == 0)
            trace(i,j) = 1;
        elseif(str2double(line{j}) == -1)
            obstical(i,j) = 1;
        elseif(str2double(line{j}) == -2)
            start(i,j) = 1;
        elseif(str2double(line{j}) == -3)
            final(i,j) = 1;
        elseif(str2double(line{j}) == 1)
            road(i,j) = 1;
        elseif(str2double(line{j}) == 5)
            grassland(i,j) = 1;
        elseif(str2double(line{j}) == 10)
            forest(i,j) = 1;
        elseif(str2double(line{j}) == 50)
            mountain(i,j) = 1;
        elseif(str2double(line{j}) == 100)
            water(i,j) = 1;
        end
    end
end

figure(1)
hold on
spy(road,'sw',10)
spy(grassland,'sg',10)
spy(forest,'sy',10)
spy(mountain,'sr',10)
spy(water,'sb',10)
spy(obstical,'sk',21)
spy(trace,'.')
spy(start,'or',12)
spy(final,'og',12)
markerSt = findall(gca,'color','r');
markerFi = findall(gca,'color','g');
markerOb = findall(gca,'color','k');
markerRo = findall(gca,'color','w');
markerGr = findall(gca,'color','g');
markerFo = findall(gca,'color','y');
markerMo = findall(gca,'color','r');
markerWa = findall(gca,'color','b');
set(markerSt,'MarkerFaceColor','r');
set(markerFi,'MarkerFaceColor','g');
set(markerOb,'MarkerFaceColor','k');
%set(markerRo,'MarkerFaceColor','w');
set(markerGr,'MarkerFaceColor','g');
set(markerFo,'MarkerFaceColor','y');
set(markerMo,'MarkerFaceColor','r');
set(markerWa,'MarkerFaceColor','b');
axis([1.5 cols-0.5 0.5 rows+0.5])
box off;
set(gca,'xcolor',get(gcf,'color'));
set(gca,'ycolor',get(gcf,'color'));
set(gca,'xtick',[]);
set(gca,'ytick',[]);
set(gca,'Color',[0.9 0.9 0.9]);




